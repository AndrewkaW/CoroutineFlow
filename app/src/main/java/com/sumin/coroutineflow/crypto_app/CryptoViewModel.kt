package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.map

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository
    val state: LiveData<State> = repository.getCurrencyList()

        .filter { currencyList ->
            currencyList.isNotEmpty()
        }
        .map { State.Content(currencyList = it) as State }
        .onStart {
            State.Loading
        }
        .asLiveData()

}
