package dev.janssenbatista.moneytracker.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.janssenbatista.moneytracker.R

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.logo),
                    contentDescription = "Money Tracker Logo",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Money",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Tracker", fontSize = 42.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

    }
}