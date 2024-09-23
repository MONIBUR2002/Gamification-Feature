package com.moniapps.gamificationfeature.screens

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.moniapps.gamificationfeature.R

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navigationBarItems = remember {
        NavigationBarItems.values()
    }
    var switchScreen by remember {
        mutableStateOf("Home")
    }
    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                modifier = Modifier
                    .height(90.dp)
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
                cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                ballAnimation = Parabolic(tween(300)),
                barColor = MaterialTheme.colorScheme.surfaceVariant,
                ballColor = MaterialTheme.colorScheme.inversePrimary,

                ) {
                navigationBarItems.forEach {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                selectedIndex = it.ordinal
                                switchScreen = if (it.name == "Home") "Home"
                                else "Wallet"
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = it.icon),
                            contentDescription = null,
                            tint = if (selectedIndex == it.ordinal) MaterialTheme.colorScheme.inversePrimary
                            else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            when(switchScreen){
                "Home" -> HomeScreen()
                "Wallet" -> WalletScreen()
            }
        }

    }

}

enum class NavigationBarItems(val icon: Int) {
    Home(icon = R.drawable.home),
    Wallet(icon = R.drawable.wallet)
}