package com.example.soundfriendlyworkplace.screens.worker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.components.WorkerBottomBar
import com.example.soundfriendlyworkplace.ui.theme.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

@Composable
fun WorkerMainScreen(
    onLogout: () -> Unit = {}
) {

    var pantalla by rememberSaveable {
        mutableStateOf("home")
    }
    var mostrarConexion by rememberSaveable {
        mutableStateOf(false)
    }
    var mostrarCerrarSesion by rememberSaveable {
        mutableStateOf(false)
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
                        mostrarConexion = true
                    },
                    onLogout = {
                        mostrarCerrarSesion = true
                    }
                )
            }
        }
    }
    if (mostrarConexion) {
        AlertDialog(
            onDismissRequest = {
                mostrarConexion = false
            },
            title = {
                Text("Estado de conexión")
            },
            text = {
                Text("La información de conexión estará disponible próximamente.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConexion = false
                    }
                ) {
                    Text("Aceptar", color = VioletaPrincipal)
                }
            }
        )
    }
    if (mostrarCerrarSesion) {
        AlertDialog(
            onDismissRequest = {
                mostrarCerrarSesion = false
            },
            title = {
                Text("Cerrar sesión")
            },
            text = {
                Text("¿Estás seguro de que querés cerrar sesión?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarCerrarSesion = false
                        onLogout()
                    }
                ) {
                    Text("Cerrar sesión", color = VioletaPrincipal)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarCerrarSesion = false
                    }
                ) {
                    Text("Cancelar", color = TextoPrincipal)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerMainPreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerMainScreen()
    }
}

