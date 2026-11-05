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
    var sectorSeleccionado by rememberSaveable {
        mutableStateOf("Taller")
    }
    // Estado temporal hasta integrar el backend.
    var alertaReconocida by rememberSaveable {
        mutableStateOf(false)
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
                    onSectorClick = { sector ->
                        sectorSeleccionado = sector
                        pantalla = "sector"
                    }
                )

                "sector" -> HygieneSectorScreen(
                    sector = sectorSeleccionado,
                    onBack = { pantalla = "home" }
                )

                "alerts" -> HygieneAlertsScreen(
                    alertaReconocida = alertaReconocida,
                    onAlertClick = {
                        pantalla = "alertDetail"
                    },
                    onViewSectors = {
                        pantalla = "home"
                    }
                )

                "alertDetail" -> HygieneAlertDetailScreen(
                    reconocida = alertaReconocida,
                    onBack = {
                        pantalla = "alerts"
                    },
                    onRecognize = {
                        alertaReconocida = true
                    }
                )
                "sensors" -> HygieneSensorsScreen()

                // TODO: Crear y conectar las demás pantallas.
                "history" -> HygieneHomeScreen()
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