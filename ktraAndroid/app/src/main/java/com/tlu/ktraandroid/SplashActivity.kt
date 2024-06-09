package com.tlu.ktraandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlu.ktraandroid.ui.theme.KtraAndroidTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtraAndroidTheme {
                SplashScreen()

            }

        }
    }

    @Composable
    @Preview(showSystemUi = true)
    private fun SplashScreen() {
        val alpha = remember{
androidx.compose.animation.core.Animatable(0f)

        }
        LaunchedEffect(key1 = true) {
    alpha.animateTo(
        1f,
        animationSpec = tween(1500)
    )
            delay(1800)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xEE1C1C55)), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.contacts),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .alpha(alpha.value)
            )
            Text(
                text = "My Contacts",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .absolutePadding(top = 150.dp)
                    .alpha(alpha.value)
            )
        }
    }
}

