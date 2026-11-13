
package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundfriendlyworkplace.data.model.AlertaDto
import com.example.soundfriendlyworkplace.ui.theme.*
import com.example.soundfriendlyworkplace.viewmodel.AlertaViewModel
import com.example.soundfriendlyworkplace.viewmodel.AlertasUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HygieneAlertsScreen(
    onAlertClick: (String) -> Unit = {},
    onViewSectors: () -> Unit = {},
    onViewSensors: () -> Unit = {}
) {
    val alertaViewModel: AlertaViewModel = viewModel()

    HygieneAlertsContent(
        estado = alertaViewModel.alertasUiState,
        onActualizar = { alertaViewModel.cargarAlertas() },
        onAlertClick = onAlertClick,
        onViewSectors = onViewSectors
    )
}

@Composable
private fun HygieneAlertsContent(
    estado: AlertasUiState,
    onActualizar: () -> Unit,
    onAlertClick: (String) -> Unit,
    onViewSectors: () -> Unit
) {
    var pestanaSeleccionada by rememberSaveable {
        mutableStateOf("Sin resolver")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = "Alertas",
            fontSize = 20.sp,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color(0xFFBDA7D5),
                    RoundedCornerShape(30.dp)
                )
                .background(
                    Color(0xFFF0E8FA),
                    RoundedCornerShape(30.dp)
                )
                .padding(3.dp)
        ) {
            listOf("Sin resolver", "Resueltas").forEach { pestana ->
                val seleccionada = pestanaSeleccionada == pestana

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (seleccionada) Color(0xFFE6D5FF)
                            else Color.Transparent,
                            RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            pestanaSeleccionada = pestana
                        }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pestana,
                        fontSize = 12.sp,
                        fontWeight = if (seleccionada) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                        color = TextoPrincipal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (estado) {
            AlertasUiState.Cargando -> {
                Text(
                    text = "Cargando alertas...",
                    modifier = Modifier.fillMaxWidth(),
                    color = TextoSecundario,
                    textAlign = TextAlign.Center
                )
            }

            is AlertasUiState.Exito -> {
                val alertasFiltradas = estado.alertas
                    .filter { alerta ->
                        if (pestanaSeleccionada == "Sin resolver") {
                            alerta.estado != "RESUELTA"
                        } else {
                            alerta.estado == "RESUELTA"
                        }
                    }
                    .sortedWith(
                        compareBy<AlertaDto> {
                            when (it.tipo) {
                                "CRITICO" -> 0
                                "ATENCION" -> 1
                                "OFFLINE" -> 2
                                else -> 3
                            }
                        }.thenByDescending { it.creadaEn }
                    )

                if (alertasFiltradas.isEmpty()) {
                    SinAlertas(
                        onViewSectors = onViewSectors,
                        mensaje = if (pestanaSeleccionada == "Sin resolver") {
                            "No hay alertas pendientes"
                        } else {
                            "No hay alertas resueltas"
                        }
                    )
                } else {
                    Text(
                        text = "Priorizadas por gravedad y fecha.",
                        fontSize = 11.sp,
                        color = TextoSecundario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    alertasFiltradas.forEach { alerta ->
                        TarjetaAlerta(
                            alerta = alerta,
                            onAlertClick = onAlertClick
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                    }
                }
            }

            is AlertasUiState.Error -> {
                Text(
                    text = estado.mensaje,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp,
                    color = Color(0xFFB71C1C),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onActualizar,
            modifier = Modifier.fillMaxWidth(),
            enabled = estado !is AlertasUiState.Cargando,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE6D5FF),
                contentColor = Color(0xFF5C35A0)
            )
        ) {
            Text("↻ Actualizar alertas")
        }
    }
}

@Composable
private fun TarjetaAlerta(
    alerta: AlertaDto,
    onAlertClick: (String) -> Unit
) {
    val esCritica = alerta.tipo == "CRITICO"
    val esAtencion = alerta.tipo == "ATENCION"
    val esOffline = alerta.tipo == "OFFLINE"

    val colorBorde = when {
        esCritica -> Color(0xFFC62828)
        esAtencion -> Color(0xFFE69A27)
        else -> Color(0xFFDCD4E6)
    }

    val colorFondo = when {
        esCritica -> Color(0xFFFFE8EB)
        esAtencion -> Color(0xFFFFF4DD)
        else -> Color.White
    }

    val nombreTipo = when {
        esCritica -> "Crítico"
        esAtencion -> "Atención"
        esOffline -> "Sensor sin conexión"
        else -> alerta.tipo
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorFondo,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                colorBorde,
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = "$nombreTipo · ${formatearFechaAlerta(alerta.creadaEn)}",
            fontSize = 11.sp,
            color = colorBorde
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (esOffline) {
                "Sensor sin conexión"
            } else {
                "${alerta.sector} · $nombreTipo"
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (alerta.valor != null) {
            Text(
                text = String.format(
                    Locale.forLanguageTag("es-AR"),
                    "%.1f %% · %s",
                    alerta.valor,
                    alerta.estado
                ),
                fontSize = 12.sp,
                color = TextoSecundario
            )
        } else {
            Text(
                text = "${alerta.deviceId} · ${alerta.estado}",
                fontSize = 12.sp,
                color = TextoSecundario
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = alerta.mensaje,
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                onAlertClick(alerta.id.toString())
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5C35A0)
            )
        ) {
            Text("Ver detalle de alerta")
        }
    }
}

// Convierte la fecha UTC del backend a la hora local del dispositivo.
private fun formatearFechaAlerta(fecha: String): String {
    return try {
        val instante = Instant.parse(fecha)

        val formato = DateTimeFormatter.ofPattern(
            "dd/MM/yyyy HH:mm",
            Locale.forLanguageTag("es-AR")
        )

        instante
            .atZone(ZoneId.systemDefault())
            .format(formato)
    } catch (e: Exception) {
        fecha
    }
}

@Composable
private fun SinAlertas(
    onViewSectors: () -> Unit,
    mensaje: String
) {
    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(
                Color(0xFFE9D9FF),
                RoundedCornerShape(14.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "✓",
            fontSize = 50.sp,
            color = Color(0xFF5C35A0)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = mensaje,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextoPrincipal,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Podés consultar el estado de los sectores desde Inicio.",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 12.sp,
        color = TextoSecundario,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = onViewSectors,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE9D9FF),
            contentColor = Color(0xFF5C35A0)
        )
    ) {
        Text("Ver sectores")
    }
}

// Preview con las tres alertas reales del JSON de prueba.
@Preview(showBackground = true)
@Composable
private fun HygieneAlertsPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneAlertsContent(
            estado = AlertasUiState.Exito(
                listOf(
                    AlertaDto(
                        id = 1,
                        sectorId = 1,
                        sector = "Taller de prueba",
                        deviceId = "nm-001",
                        tipo = "ATENCION",
                        valor = 80.4,
                        mensaje = "Taller de prueba: la exposición del turno llegó al 80 % del límite diario. Revisá el sector.",
                        estado = "PENDIENTE",
                        creadaEn = "2026-11-12T23:43:22Z",
                        reconocidaPor = null,
                        reconocidaEn = null,
                        resueltaPor = null,
                        resueltaEn = null,
                        actualizadoEn = "2026-11-12T23:43:22Z"
                    ),
                    AlertaDto(
                        id = 2,
                        sectorId = 1,
                        sector = "Taller de prueba",
                        deviceId = "nm-001",
                        tipo = "CRITICO",
                        valor = 100.2,
                        mensaje = "Taller de prueba: la exposición del turno llegó al 100 % del límite diario. Registrá una intervención.",
                        estado = "PENDIENTE",
                        creadaEn = "2026-11-12T23:43:22Z",
                        reconocidaPor = null,
                        reconocidaEn = null,
                        resueltaPor = null,
                        resueltaEn = null,
                        actualizadoEn = "2026-11-12T23:43:22Z"
                    ),
                    AlertaDto(
                        id = 3,
                        sectorId = 1,
                        sector = "Taller de prueba",
                        deviceId = "nm-001",
                        tipo = "OFFLINE",
                        valor = null,
                        mensaje = "Taller de prueba: el sensor nm-001 dejó de comunicarse.",
                        estado = "PENDIENTE",
                        creadaEn = "2026-11-12T23:43:22Z",
                        reconocidaPor = null,
                        reconocidaEn = null,
                        resueltaPor = null,
                        resueltaEn = null,
                        actualizadoEn = "2026-11-12T23:43:22Z"
                    )
                )
            ),
            onActualizar = {},
            onAlertClick = {},
            onViewSectors = {}
        )
    }
}

// Preview del estado vacío.
@Preview(showBackground = true)
@Composable
private fun HygieneAlertsEmptyPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneAlertsContent(
            estado = AlertasUiState.Exito(emptyList()),
            onActualizar = {},
            onAlertClick = {},
            onViewSectors = {}
        )
    }
}


