package com.moniapps.gamificationfeature.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.moniapps.gamificationfeature.R
import com.moniapps.gamificationfeature.ui.theme.goldColor
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    walletViewModel: WalletViewModel
) {


    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navigationBarItems = rememberSaveable {
        NavigationBarItems.entries.toTypedArray()
    }
    var switchScreen by rememberSaveable {
        mutableStateOf("Home")
    }
    var showAddCoinDialog by remember {
        mutableStateOf(false)
    }
    val newCoins = walletViewModel.newCoins.collectAsState()
    val coinAdded = walletViewModel.coinAdded.collectAsState()

    val coinRainAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.coin_animation)
    )
    LaunchedEffect(key1 = walletViewModel.coinAdded, key2 = Unit) {
        showAddCoinDialog = true
    }
    Scaffold(bottomBar = {
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
                        }, contentAlignment = Alignment.Center
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
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (coinAdded.value) {

                BasicAlertDialog(onDismissRequest = { walletViewModel.dismissAlertDialog() }) {
                    Card(
                        border = BorderStroke(
                            width = 8.dp, brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color(0xFFFFEE00), // Light Gold
                                    Color(0xFFB8860B), // Dark Gold
                                    Color(0xFFCFB53B), // Old Gold
                                    Color(0xFFD4AF37), // Metallic Gold
                                    Color(0xFFEEE8AA)
                                ),
                                center = Offset.Zero
                            )
                        ),
                        shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box {
                                LottieAnimation(
                                    modifier = Modifier.size(width = 200.dp, height = 250.dp),
                                    composition = coinRainAnimation,
                                    iterations = LottieConstants.IterateForever
                                )
                                Text(
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center),
                                    text = newCoins.value.toString() + " Coins",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontSize = 50.sp,
                                    color = goldColor,
                                    fontWeight = FontWeight.Bold,
                                )
                            }

                            Text(text = "You got new coins", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            AnimatedContent(targetState = switchScreen, transitionSpec = {
                slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn), towards = Left


                ).togetherWith(
                    slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseIn), towards = Right

                    )
                )
            }, label = "") { targetState ->
                when (targetState) {
                    "Home" -> HomeScreen()
                    "Wallet" -> WalletScreen(
                        navHostController = navHostController, walletViewModel = walletViewModel
                    )
                }

            }

        }

    }

}

enum class NavigationBarItems(val icon: Int) {
    Home(icon = R.drawable.home), Wallet(icon = R.drawable.wallet)
}