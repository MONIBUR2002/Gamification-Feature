package com.moniapps.gamificationfeature.screens

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.moniapps.gamificationfeature.R
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, navHostController: NavHostController, walletViewModel: WalletViewModel) {
    
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navigationBarItems = rememberSaveable {
        NavigationBarItems.entries.toTypedArray()
    }
    var switchScreen by rememberSaveable {
        mutableStateOf("Home")
    }
    var showAddCoinDialog by remember{
        mutableStateOf(false)
    }
    val newCoins = walletViewModel.newCoins.collectAsState()
    val coinAdded = walletViewModel.coinAdded.collectAsState()
    LaunchedEffect(key1 = walletViewModel.coinAdded, key2 = Unit) {
        showAddCoinDialog = true
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
                            tint = if (selectedIndex == it.ordinal) MaterialTheme.colorScheme.inversePrimary.copy(
                                alpha = 0.8f
                            )
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
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
            if (coinAdded.value){
                AlertDialog(onDismissRequest = { walletViewModel.dismissAlertDialog() }) {
                    Card (
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        )
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "You got ${newCoins.value} new coins")
                        }
                    }
                }
            }
            
            AnimatedContent(
                targetState = switchScreen,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = Left


                    ).togetherWith(
                        slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseIn),
                            towards = Right

                        )
                    )
                }
            ) { targetState ->
                when (targetState) {
                    "Home" -> HomeScreen()
                    "Wallet" -> WalletScreen(navHostController = navHostController, walletViewModel = walletViewModel)
                }

            }

        }

    }

}

enum class NavigationBarItems(val icon: Int) {
    Home(icon = R.drawable.home),
    Wallet(icon = R.drawable.wallet)
}