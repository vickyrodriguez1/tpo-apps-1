package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.soundfriendlyworkplace.data.model.AlertaDto
import com.example.soundfriendlyworkplace.ui.theme.*
import com.example.soundfriendlyworkplace.viewmodel.AlertaDetalleUiState
import com.example.soundfriendlyworkplace.viewmodel.AlertaViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HygieneAlertDetailScreen(
    alertaId: Int,
    onBack: () -> Unit
) {
    val alertaViewModel: AlertaViewModel = viewModel()

    LaunchedEffect(alertaId) {
        alertaViewModel.cargarAlertaPorId(alertaId)
    }

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
                text = "Detalle de alerta",
                fontSize = 20.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        when (val estado = alertaViewModel.alertaDetalleUiState) {
            AlertaDetalleUiState.Cargando -> {
                Text(
                    text = "Cargando detalle...",
                    color = TextoSecundario
                )
            }

            is AlertaDetalleUiState.Exito -> {
                HygieneAlertDetailContent(
                    alerta = estado.alerta
                )
            }

            is AlertaDetalleUiState.Error -> {
                Text(
                    text = estado.mensaje,
                    color = Color(0xFFC62828)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver a las alertas",
                color = Color(0xFF5C35A0)
            )
        }
    }
}

@Composable
private fun HygieneAlertDetailContent(
    alerta: AlertaDto
) {
    val colorAlerta = when (alerta.tipo) {
        "CRITICO" -> Color(0xFFC62828)
        "ATENCION" -> Color(0xFFE69A27)
        else -> Color(0xFF5C35A0)
    }

    val fondoAlerta = when (alerta.tipo) {
        "CRITICO" -> Color(0xFFFFE8EB)
        "ATENCION" -> Color(0xFFFFF4DD)
        else -> Color.White
    }

    val tipoAlerta = when (alerta.tipo) {
        "CRITICO" -> "Crítico"
        "ATENCION" -> "Atención"
        "OFFLINE" -> "Sensor sin conexión"
        else -> alerta.tipo
    }

    Text(
        text = "ⓘ $tipoAlerta · ${alerta.estado}",
        fontSize = 12.sp,
        color = colorAlerta
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Sector",
        fontSize = 12.sp,
        color = Color(0xFF5C35A0)
    )

    Text(
        text = alerta.sector,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextoPrincipal
    )

    Text(
        text = "${formatearFechaDetalle(alerta.creadaEn)} · Sensor ${alerta.deviceId}",
        fontSize = 11.sp,
        color = TextoSecundario
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                fondoAlerta,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                colorAlerta,
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = if (alerta.valor != null) {
                    "Exposición del turno"
                } else {
                    "Comunicación"
                },
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Text(
                text = if (alerta.valor != null) {
                    String.format(
                        Locale.forLanguageTag("es-AR"),
                        "%.1f %%",
                        alerta.valor
                    )
                } else {
                    "Sin conexión"
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
        }

        Column {
            Text(
                text = "Tipo de alerta",
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Text(
                text = tipoAlerta,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorAlerta
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Color(0xFFDCD4E6),
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Información de la alerta",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(14.dp))

        FilaInformacionAlerta("Estado", alerta.estado)
        FilaInformacionAlerta("Sector", alerta.sector)
        FilaInformacionAlerta("Sensor", alerta.deviceId)
        FilaInformacionAlerta(
            "Fecha de creación",
            formatearFechaDetalle(alerta.creadaEn)
        )

        if (alerta.reconocidaPor != null) {
            FilaInformacionAlerta(
                "Reconocida por",
                alerta.reconocidaPor
            )
        }

        if (alerta.reconocidaEn != null) {
            FilaInformacionAlerta(
                "Reconocida el",
                formatearFechaDetalle(alerta.reconocidaEn)
            )
        }

        if (alerta.resueltaPor != null) {
            FilaInformacionAlerta(
                "Resuelta por",
                alerta.resueltaPor
            )
        }

        if (alerta.resueltaEn != null) {
            FilaInformacionAlerta(
                "Resuelta el",
                formatearFechaDetalle(alerta.resueltaEn)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = alerta.mensaje,
            fontSize = 12.sp,
            color = TextoSecundario
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (alerta.estado == "PENDIENTE") {
        Text(
            text = "El reconocimiento estará disponible cuando se integre con el backend.",
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE6D5FF),
                contentColor = Color(0xFF5C35A0)
            )
        ) {
            Text("Reconocer alerta")
        }
    } else if (alerta.reconocidaEn != null) {
        Text(
            text = "✓ Alerta reconocida",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )
    }
}

@Composable
private fun FilaInformacionAlerta(
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = valor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )
    }
}

private fun formatearFechaDetalle(fecha: String): String {
    return try {
        Instant.parse(fecha)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm",
                    Locale.forLanguageTag("es-AR")
                )
            )
    } catch (e: Exception) {
        fecha
    }
}

@Preview(showBackground = true)
@Composable
private fun HygieneAlertDetailPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneAlertDetailContent(
            alerta = AlertaDto(
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
            )
        )
    }
}




