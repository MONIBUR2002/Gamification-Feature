package com.moniapps.gamificationfeature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moniapps.gamificationfeature.ui.theme.GamificationFeatureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamificationFeatureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Project(
                        name = "Gamification Feature",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Project(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Initial commit $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GamificationFeatureTheme {
        Project("Gamification Feature")
    }
}