package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.components.HygieneBottomBar
import com.example.soundfriendlyworkplace.ui.theme.FondoLavanda
import com.example.soundfriendlyworkplace.ui.theme.SoundFriendlyWorkplaceTheme
import com.example.soundfriendlyworkplace.ui.theme.TextoPrincipal
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundfriendlyworkplace.data.local.DatabaseProvider
import com.example.soundfriendlyworkplace.data.repository.SectorRepository
import com.example.soundfriendlyworkplace.viewmodel.HygieneViewModel
import com.example.soundfriendlyworkplace.viewmodel.HygieneViewModelFactory

@Composable
fun HygieneMainScreen() {
    val context = LocalContext.current

    val hygieneViewModel: HygieneViewModel = viewModel(
        factory = HygieneViewModelFactory(
            SectorRepository(
                sectorDao = DatabaseProvider
                    .obtenerBaseDeDatos(context)
                    .sectorDao()
            )
        )
    )

    // Pantalla seleccionada.
    var pantalla by rememberSaveable {
        mutableStateOf("home")
    }

    // ID del sector utilizado para consultar el backend.
    var sectorIdSeleccionado by rememberSaveable {
        mutableIntStateOf(0)
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

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (pantalla) {

                "home" -> HygieneHomeScreen(
                    hygieneViewModel = hygieneViewModel,
                    onSectorClick = { id, _ ->
                        sectorIdSeleccionado = id
                        pantalla = "sector"
                    }
                )

                "sector" -> HygieneSectorScreen(
                    sectorId = sectorIdSeleccionado,
                    hygieneViewModel = hygieneViewModel,
                    onBack = {
                        pantalla = "home"
                    }
                )

                "alerts" -> HygieneAlertsScreen(
                    onAlertClick = {
                        pantalla = "alertDetail"
                    },
                    onViewSectors = {
                        pantalla = "home"
                    },
                    onViewSensors = {
                        pantalla = "sensors"
                    }
                )

                "alertDetail" -> HygieneAlertDetailScreen(
                    reconocida = false,
                    onBack = {
                        pantalla = "alerts"
                    },
                    onRecognize = {
                        // TODO: Implementar cuando el backend
                        // permita registrar reconocimientos.
                    }
                )

                "sensors" -> HygieneSensorsScreen()

                "history" -> PantallaPendiente(
                    titulo = "Historial"
                )

                "profile" -> PantallaPendiente(
                    titulo = "Perfil"
                )

                else -> PantallaPendiente(
                    titulo = "Pantalla no disponible"
                )
            }
        }
    }
}

// Componente temporal para pantallas todavía no implementadas.
@Composable
private fun PantallaPendiente(
    titulo: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$titulo\n\nPantalla pendiente de implementar.",
            fontSize = 16.sp,
            color = TextoPrincipal,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneMainPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneMainScreen()
    }
}