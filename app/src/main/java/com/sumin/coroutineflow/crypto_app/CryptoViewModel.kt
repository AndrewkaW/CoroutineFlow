package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val loadFlow = MutableSharedFlow<State>()

    fun loadList() {
        viewModelScope.launch {
            loadFlow.emit(State.Loading)
            repository.updateList()
        }
    }

    private val repository = CryptoRepository

    val state: Flow<State> =  repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart {
            emit(State.Loading)
        }
        .mergeWith(loadFlow)

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T>{
        return merge(this, another)
    }
}
