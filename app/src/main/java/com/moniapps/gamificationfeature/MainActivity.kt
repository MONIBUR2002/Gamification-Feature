package com.moniapps.gamificationfeature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.moniapps.gamificationfeature.navigation.NavigationGraph
import com.moniapps.gamificationfeature.screens.MainScreen
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
