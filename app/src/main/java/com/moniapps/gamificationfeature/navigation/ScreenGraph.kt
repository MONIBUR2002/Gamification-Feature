package com.moniapps.gamificationfeature.navigation

sealed class ScreenGraph (var route: String){
    data object MainScreen: ScreenGraph("Main_Screen")
    data object AddMoneyScreen: ScreenGraph("Add_Money_Screen")
    data object RedeemScreen: ScreenGraph("Redeem_Screen")
}