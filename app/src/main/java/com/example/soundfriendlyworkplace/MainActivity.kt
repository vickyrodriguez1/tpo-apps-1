package com.example.soundfriendlyworkplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.SoundFriendlyWorkplaceTheme
import com.example.soundfriendlyworkplace.screens.LoginScreen
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.soundfriendlyworkplace.screens.RecoveryScreen
import com.example.soundfriendlyworkplace.screens.CodeVerificationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoundFriendlyWorkplaceTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var pantalla by rememberSaveable {
                        mutableStateOf("login")
                    }
                    var correoRecuperacion by rememberSaveable {
                        mutableStateOf("")
                    }

                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (pantalla) {
                            "login" -> LoginScreen(
                                onForgotPassword = {
                                    pantalla = "recovery"
                                }
                            )

                            "recovery" -> RecoveryScreen(
                                onSendCode = { correo ->
                                    correoRecuperacion = correo
                                    pantalla = "verification"
                                },
                                onBack = {
                                    pantalla = "login"
                                }
                            )

                            "verification" -> CodeVerificationScreen(
                                correo = correoRecuperacion,
                                onBack = {
                                    pantalla = "recovery"
                                }
                            )
                        }
                    }
                }

            }
    }
}
}

@Composable
fun greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoundFriendlyWorkplaceTheme {
        greeting("Android")
    }
}