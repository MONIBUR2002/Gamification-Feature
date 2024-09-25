package com.moniapps.gamificationfeature.screens

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moniapps.gamificationfeature.R
import com.moniapps.gamificationfeature.navigation.ScreenGraph
import com.moniapps.gamificationfeature.ui.theme.addMoneyButtonColor
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

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
    var coinAlertDialog by remember {

        mutableStateOf(false)
    }

    val progress = animateFloatAsState(
        targetValue = transaction / 5f,
        animationSpec = tween(5000, easing = LinearOutSlowInEasing)
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wallet",
                        style = MaterialTheme.typography.headlineLarge
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
            if (coinAlertDialog) {
                AlertDialog(onDismissRequest = { coinAlertDialog = false }) {
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
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    progress = progress.value
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
                            onClick = { navHostController.navigate(route = ScreenGraph.AddMoneyScreen.route) },
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
                Spacer(modifier = Modifier.height(80.dp))
                Button(
                    modifier = Modifier.size(width = 400.dp, height = 80.dp),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(10.dp),
                    enabled = redeemable
                ) {
                    if (redeemable) Text(text = "Redeem coins")
                    else Text(
                        text = "${(5 - transaction).toInt()} transactions left to redeem",
                        style = MaterialTheme.typography.bodyMedium
                    )


                }

            }

        }
    }
}

@Preview
@Composable
private fun WalletScreenPreview() {

}