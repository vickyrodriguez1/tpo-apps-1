package com.example.soundfriendlyworkplace.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneBottomBar(
    selectedScreen: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        "Inicio" to "home",
        "Alertas" to "alerts",
        "Historial" to "history",
        "Sensores" to "sensors",
        "Perfil" to "profile"
    )

    NavigationBar(
        containerColor = FondoLavanda
    ) {
        items.forEach { (label, screen) ->
            NavigationBarItem(
                selected = selectedScreen == screen,
                onClick = { onNavigate(screen) },
                icon = {
                    Text(
                        text = when (screen) {
                            "home" -> "⌂"
                            "alerts" -> "♧"
                            "history" -> "◷"
                            "sensors" -> "▥"
                            else -> "♙"
                        }
                    )
                },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VioletaPrincipal,
                    selectedTextColor = VioletaPrincipal,
                    indicatorColor = LavandaClaro,
                    unselectedIconColor = TextoSecundario,
                    unselectedTextColor = TextoSecundario
                )
            )
        }
    }
}