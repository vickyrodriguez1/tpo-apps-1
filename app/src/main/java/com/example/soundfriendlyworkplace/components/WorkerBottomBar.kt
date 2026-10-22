package com.example.soundfriendlyworkplace.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun WorkerBottomBar(
    selectedScreen: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        "Inicio" to "home",
        "Mi sector" to "sector",
        "Historial" to "history",
        "Perfil" to "profile"
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = FondoLavanda
    ) {
        items.forEach { (label, route) ->
            NavigationBarItem(
                selected = selectedScreen == route,
                onClick = { onNavigate(route) },
                icon = {
                    Text(
                        text = when (route) {
                            "home" -> "⌂"
                            "sector" -> "▥"
                            "history" -> "◷"
                            else -> "♙"
                        }
                    )
                },
                label = { Text(label) },
                colors = androidx.compose.material3
                    .NavigationBarItemDefaults.colors(
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