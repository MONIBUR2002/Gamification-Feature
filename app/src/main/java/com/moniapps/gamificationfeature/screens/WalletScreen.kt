package com.moniapps.gamificationfeature.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moniapps.gamificationfeature.R
import com.moniapps.gamificationfeature.navigation.ScreenGraph
import com.moniapps.gamificationfeature.ui.theme.addMoneyButtonColor
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    walletViewModel: WalletViewModel
) {
    val walletBalance by walletViewModel.balance.collectAsState()
    val coins by walletViewModel.coins.collectAsState()
    val transaction by walletViewModel.transaction.collectAsState()
    val redeemable by walletViewModel.redeemable.collectAsState()
    val isRedeem by walletViewModel.isRedeemed.collectAsState()
    var coinAlertDialog by remember {

        mutableStateOf(false)
    }
    val paymentProcessingAnimation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.transation_animation)
    )
    LaunchedEffect(key1 = isRedeem) {
        delay(2200)
        walletViewModel.isRedeemToggle(toggle = false)
    }
    var hasNotificationPermission by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasNotificationPermission = isGranted
    }
    if (!hasNotificationPermission) {
        SideEffect {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val progress = animateFloatAsState(
        targetValue = transaction / 5f,
        animationSpec = tween(2000, easing = LinearOutSlowInEasing), label = "progressAnimation"
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wallet",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                actions = {
                    Card(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                coinAlertDialog = true
                            },
                        shape = RoundedCornerShape(30.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "Coin"
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                text = coins.toString()
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (isRedeem) {
                BasicAlertDialog(onDismissRequest = { walletViewModel.isRedeemToggle(toggle = false) }) {
                    Card(
                        border = BorderStroke(
                            width = 8.dp, brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color(0xFF0000FF), // Blue
                                    Color(0xFF007FFF), // Azure
                                    Color(0xFF1E90FF), // Dodger Blue
                                    Color(0xFF6495ED), // Cornflower Blue
                                    Color(0xFF7B68EE), // Medium Slate Blue
                                    Color(0xFF4169E1), // Royal Blue
                                    Color(0xFF00008B), // Dark Blue
                                    Color(0xFF000080), // Navy
                                    Color(0xFFADD8E6), // Light Blue
                                    Color(0xFF87CEEB), // Sky Blue
                                    Color(0xFF87CEFA), // Light Sky Blue
                                    Color(0xFFB0C4DE), // Light Steel Blue
                                    Color(0xFF191970), // Midnight Blue
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
                            LottieAnimation(
                                modifier = Modifier.size(width = 200.dp, height = 250.dp),
                                composition = paymentProcessingAnimation,
                                iterations = LottieConstants.IterateForever
                            )
                            Text(
                                text = "Payment successful",
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )


                        }
                    }
                }
            }

            if (coinAlertDialog) {
                BasicAlertDialog(onDismissRequest = { coinAlertDialog = false }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
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

                            Text(
                                text = if (transaction < 5)
                                    "You can redeem your coins after ${(5 - transaction).toInt()} more transactions"
                                else "You can redeem your coins now",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = {
                        progress.value
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                Card(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {

                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "₹$walletBalance",
                            style = MaterialTheme.typography.headlineSmall,
                            fontStyle = FontStyle.Italic,
                            fontSize = 40.sp
                        )
                        Button(
                            onClick = {
                                navHostController.navigate(route = ScreenGraph.AddMoneyScreen.route)
                            },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = addMoneyButtonColor
                            ),
                            border = BorderStroke(width = 1.dp, color = Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.AddCircle,
                                    contentDescription = "Add",
                                    tint = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                    text = "Add money",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp)
                ) {
                    Checkbox(checked = transaction >= 5, onCheckedChange = {})
                    Text(text = "Completed 5 transaction")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp)
                ) {
                    Checkbox(checked = coins >= 500, onCheckedChange = {})
                    Text(text = "Collect 500 coin")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .size(width = 400.dp, height = 80.dp)
                        .padding(horizontal = 4.dp),
                    onClick = {
                        walletViewModel.calculateRedeemableCoins()
                        navHostController.navigate(route = ScreenGraph.RedeemScreen.route)
                    },
                    shape = RoundedCornerShape(10.dp),
                    enabled = redeemable && coins >= 500
                ) {
                    if (redeemable) Text(text = "Redeem coins")
                    else if (coins < 500) Text(text = "Collect ${500 - coins} more coins to redeem")
                    else Text(
                        text = "${(5 - transaction).toInt()} transactions left to redeem",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

