package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.components.HygieneBottomBar
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneMainScreen() {

    var pantalla by rememberSaveable {
        mutableStateOf("home")
    }

    Scaffold(
        containerColor = FondoLavanda,
        bottomBar = {
            HygieneBottomBar(
                selectedScreen = pantalla,
                onNavigate = { pantalla = it }
            )
        }
    ) { innerPadding ->

        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (pantalla) {
                "home" -> HygieneHomeScreen(
                    onSectorClick = {
                        pantalla = "sector"
                    }
                )

                "sector" -> HygieneSectorScreen(
                    onBack = {
                        pantalla = "home"
                    }
                )

                // TODO: Crear y conectar las demás pantallas.
                "alerts" -> HygieneHomeScreen()
                "history" -> HygieneHomeScreen()
                "sensors" -> HygieneHomeScreen()
                "profile" -> HygieneHomeScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneMainPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneMainScreen()
    }
}