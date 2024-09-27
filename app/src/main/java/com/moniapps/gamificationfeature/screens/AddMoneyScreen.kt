package com.moniapps.gamificationfeature.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moniapps.gamificationfeature.notification.PaymentNotificationService
import com.moniapps.gamificationfeature.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMoneyScreen(navHostController: NavHostController, walletViewModel: WalletViewModel) {
    val walletBalance by walletViewModel.balance.collectAsState()
    val context = LocalContext.current
    val notificationService = PaymentNotificationService(context)
    var amount by remember { mutableStateOf("") }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                // modifier = Modifier.padding(top = 32.dp),
                title = { Text(text = "Add Money") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "₹$walletBalance available")
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = amount, onValueChange = { amount = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1,
                    label = { Text(text = "Enter amount") },
                    keyboardActions = KeyboardActions(

                        onDone = {
                            if (amount.isNotEmpty() && amount.toInt() >= 100){
                                walletViewModel.addAmount(amount.toDouble())
                                walletViewModel.addCoins(amount.toInt())
                                walletViewModel.redeemCoinsCondition()
                                notificationService.showNotification(title = "Payment Successful", message = "₹${amount.toInt()} added to your wallet.")
                                navHostController.navigateUp()
                            }

                        }
                    ),
                    isError = amount.isEmpty()

                )
                if (amount.isNotEmpty() && amount.toInt() < 100){
                    Card(
                        modifier = Modifier.padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = androidx.compose.ui.graphics.Color.Red
                        )
                    ) {
                        Text(modifier = Modifier.padding(horizontal = 8.dp),text = "Minimum amount should be ₹100")
                    }
                }
            }

        }

    }
}
