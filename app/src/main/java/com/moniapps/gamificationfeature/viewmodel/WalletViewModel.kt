package com.moniapps.gamificationfeature.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WalletViewModel : ViewModel() {

    private val _balance = MutableStateFlow<Double>(0.0)
    val balance = _balance.asStateFlow()
    private var _coins = MutableStateFlow(0)
    val coins = _coins.asStateFlow()
    private var _newCoins = MutableStateFlow(0)
    val newCoins = _newCoins.asStateFlow()
    private var _coinAdded = MutableStateFlow(false)
    val coinAdded = _coinAdded.asStateFlow()
    private var _transaction = MutableStateFlow(0f)
    val transaction = _transaction.asStateFlow()
    private var _redeemable = MutableStateFlow(false)
    val redeemable = _redeemable.asStateFlow()

    fun addAmount(amount: Double) {
        _balance.value += amount
    }

    fun addCoins(amount: Int) {
        _newCoins.value = (amount * 0.1).toInt()
        _coins.value += newCoins.value
        _transaction.value += 1f
        _coinAdded.value = true
    }
    fun dismissAlertDialog() {
        _coinAdded.value = false
    }
    fun redeemCoins() {
        if (transaction.value > 4f) _redeemable.value = true
    }



}