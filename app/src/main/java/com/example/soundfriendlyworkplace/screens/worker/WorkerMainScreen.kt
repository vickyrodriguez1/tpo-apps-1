package com.example.soundfriendlyworkplace.screens.worker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.components.WorkerBottomBar
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun WorkerMainScreen() {

    var pantalla by rememberSaveable {
        mutableStateOf("home")
    }

    Scaffold(
        containerColor = FondoLavanda,

        bottomBar = {
            WorkerBottomBar(
                selectedScreen = pantalla,
                onNavigate = { nuevaPantalla ->
                    pantalla = nuevaPantalla
                }
            )
        }
    ) { innerPadding ->

        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (pantalla) {

                "home" -> WorkerHomeScreen(
                    onSectorClick = {
                        pantalla = "sector"
                    },
                    onHistoryClick = {
                        pantalla = "history"
                    }
                )

                "sector" -> WorkerSectorScreen(
                    onBack = {
                        pantalla = "home"
                    },
                    onHistoryClick = {
                        pantalla = "history"
                    }
                )

                "history" -> WorkerHistoryScreen()

                "profile" -> WorkerProfileScreen(
                    onConnectionClick = {
                        // Pendiente: mostrar estado de conexión
                    },
                    onLogout = {
                        // Pendiente: conectar con el cierre de sesión
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerMainPreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerMainScreen()
    }
}

