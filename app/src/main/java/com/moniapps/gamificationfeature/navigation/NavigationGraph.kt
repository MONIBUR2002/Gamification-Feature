package com.moniapps.gamificationfeature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moniapps.gamificationfeature.screens.AddMoneyScreen
import com.moniapps.gamificationfeature.screens.MainScreen
import com.moniapps.gamificationfeature.screens.RedeemScreen
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

@Composable
fun NavigationGraph(navHostController: NavHostController, walletViewModel: WalletViewModel) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenGraph.MainScreen.route
    ) {
        composable(route = ScreenGraph.MainScreen.route){
            MainScreen(navHostController = navHostController, walletViewModel = walletViewModel)
        }

        composable(route = ScreenGraph.AddMoneyScreen.route){
            AddMoneyScreen(navHostController = navHostController, walletViewModel = walletViewModel)
        }
        composable(route = ScreenGraph.RedeemScreen.route){
            RedeemScreen(navHostController = navHostController, walletViewModel = walletViewModel)
        }
    }
}