package com.moniapps.gamificationfeature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.moniapps.gamificationfeature.navigation.NavigationGraph
import com.moniapps.gamificationfeature.ui.theme.GamificationFeatureTheme
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val wallerViewModel by viewModels<WalletViewModel>()
        setContent {
            val navigationHostController = rememberNavController()
            GamificationFeatureTheme {

                NavigationGraph(navHostController = navigationHostController,wallerViewModel)

            }
        }
    }

}
