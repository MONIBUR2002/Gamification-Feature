package com.moniapps.gamificationfeature.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WalletViewModel : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
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
    private var _redeemableCoin = MutableStateFlow(0)
    val redeemableCoin = _redeemableCoin.asStateFlow()
    private var _isRedeemed = MutableStateFlow(false)
    val isRedeemed = _isRedeemed.asStateFlow()



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
    fun redeemCoinsCondition() {
        if (transaction.value >= 5f) _redeemable.value = true
    }
    fun calculateRedeemableCoins() {
        _redeemableCoin.value = (_coins.value *80) / 100 //20% of coins
    }
    fun redeemCoin(){
        _coins.value -= redeemableCoin.value
    }
    fun isRedeemToggle(toggle: Boolean){
        _isRedeemed.value = toggle
    }

}