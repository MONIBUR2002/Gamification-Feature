package com.moniapps.gamificationfeature.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moniapps.gamificationfeature.notification.PaymentNotificationService
import com.moniapps.gamificationfeature.ui.theme.goldColor
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeemScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    walletViewModel: WalletViewModel
) {
    val coins by walletViewModel.coins.collectAsState()
    val redeemableCoin by walletViewModel.redeemableCoin.collectAsState()
    var upiId by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
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
    val notificationService = PaymentNotificationService(LocalContext.current)
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Redeem Page", style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            })
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = 4.dp, brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFEE00), // Light Gold
                                Color(0xFFB8860B), // Dark Gold
                                Color(0xFFCFB53B), // Old Gold
                                Color(0xFFD4AF37), // Metallic Gold
                                Color(0xFFEEE8AA)
                            )
                        )
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "$coins coins",
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 20.sp,
                        color = goldColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "80% of the coins are redeemable coins",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        text = "$redeemableCoin",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    text = "Enter the UPI ID where you want to redeem your coins."
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = upiId,
                    onValueChange = {
                        upiId = it
                    },
                    label = {
                        Text(text = "Enter UPI ID")
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(force = false)
                        }
                    ),
                    isError = upiId.isEmpty()
                )
                if (coins <= 2) Text(modifier = Modifier.padding(top = 4.dp),text = "You don't have enough coins to redeem")
                Button(
                    onClick = {

                        walletViewModel.redeemCoin()
                        focusManager.clearFocus(false)
                        walletViewModel.isRedeemToggle(toggle = true)
                        notificationService.showNotification(title = "Redeem successful", message = "You have successfully redeemed $redeemableCoin coins.")
                        navHostController.navigateUp()
                    },
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 16.dp), shape = RoundedCornerShape(10.dp),
                    enabled = coins > 2 && upiId.isNotEmpty()
                ) {
                    Text(text = "Redeem")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RedeemPage() {
    RedeemScreen(
        modifier = Modifier,
        walletViewModel = WalletViewModel(),
        navHostController = NavHostController(
            LocalContext.current
        )
    )
}