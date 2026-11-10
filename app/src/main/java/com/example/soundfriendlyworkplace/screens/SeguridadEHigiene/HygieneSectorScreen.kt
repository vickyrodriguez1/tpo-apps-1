package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundfriendlyworkplace.data.model.SectorDto
import com.example.soundfriendlyworkplace.data.model.SensorDto
import com.example.soundfriendlyworkplace.ui.theme.*
import com.example.soundfriendlyworkplace.viewmodel.HygieneViewModel
import com.example.soundfriendlyworkplace.viewmodel.SectorDetalleUiState
import java.util.Locale

// Pantalla real: obtiene los datos del backend.
@Composable
fun HygieneSectorScreen(
    sectorId: Int,
    onBack: () -> Unit = {},
    hygieneViewModel: HygieneViewModel = viewModel()
) {
    LaunchedEffect(sectorId) {
        hygieneViewModel.cargarSectorPorId(sectorId)
    }

    HygieneSectorContent(
        estado = hygieneViewModel.sectorDetalleUiState,
        onBack = onBack,
        onReintentar = {
            hygieneViewModel.cargarSectorPorId(sectorId)
        }
    )
}

// Contenido visual reutilizable para la pantalla real y la Preview.
@Composable
private fun HygieneSectorContent(
    estado: SectorDetalleUiState,
    onBack: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(
                    text = "‹",
                    fontSize = 30.sp,
                    color = TextoPrincipal
                )
            }

            Text(
                text = when (estado) {
                    is SectorDetalleUiState.Exito -> estado.sector.nombre
                    else -> "Detalle del sector"
                },
                fontSize = 20.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (estado) {
            is SectorDetalleUiState.Cargando -> {
                Text(
                    text = "Cargando información del sector...",
                    color = TextoSecundario
                )
            }

            is SectorDetalleUiState.Error -> {
                Text(
                    text = estado.mensaje,
                    color = Color(0xFFB71C1C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onReintentar) {
                    Text("Reintentar")
                }
            }

            is SectorDetalleUiState.Exito -> {
                val sector = estado.sector

                val nombreEstado = when (sector.estado) {
                    "NORMAL" -> "Normal"
                    "ATENCION" -> "Atención"
                    "CRITICO" -> "Crítico"
                    else -> "Sin información"
                }

                val colorEstado = when (sector.estado) {
                    "NORMAL" -> Color(0xFF2E7D32)
                    "ATENCION" -> Color(0xFF996000)
                    "CRITICO" -> Color(0xFFC62828)
                    else -> Color.Gray
                }

                val colorFondo = when (sector.estado) {
                    "NORMAL" -> Color(0xFFE4F3E8)
                    "ATENCION" -> Color(0xFFFFEFCB)
                    "CRITICO" -> Color(0xFFFFE8EB)
                    else -> Color(0xFFECECEC)
                }

                val nivelRuido = sector.nivelDba?.let {
                    String.format(Locale.getDefault(), "%.1f dBA", it)
                } ?: "Sin datos"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sensor ${sector.sensor?.id ?: "sin asignar"}",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )

                    Text(
                        text = "ⓘ $nombreEstado",
                        fontSize = 12.sp,
                        color = colorEstado
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            colorFondo,
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            1.dp,
                            colorEstado,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Nivel actual",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )

                            Text(
                                text = nivelRuido,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                        }

                        Column {
                            Text(
                                text = "Estado del sector",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )

                            Text(
                                text = nombreEstado,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorEstado
                            )

                            Text(
                                text = "Datos del backend",
                                fontSize = 10.sp,
                                color = TextoSecundario
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TODO: Integrar GET /api/v1/sectores/{id}/mediciones.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Ruido durante el turno",
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Gráfico de mediciones pendiente de integrar",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

// Datos ficticios solamente para visualizar el diseño.
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneSectorPreview() {
    val sectorEjemplo = SectorDto(
        id = 1,
        nombre = "Taller",
        planta = "Planta industrial",
        estado = "CRITICO",
        nivelDba = 91.0,
        exposicionPct = 106.0,
        ultimaActualizacion = "2026-11-09T13:42:00Z",
        sensor = SensorDto("S-003", "CONECTADO"),
        alertasPendientes = 1
    )

    SoundFriendlyWorkplaceTheme {
        HygieneSectorContent(
            estado = SectorDetalleUiState.Exito(sectorEjemplo)
        )
    }
}
