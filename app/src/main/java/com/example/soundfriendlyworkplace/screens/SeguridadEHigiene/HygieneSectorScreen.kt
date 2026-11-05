package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneSectorScreen(
    sector: String,
    onBack: () -> Unit = {},
) {
    // TODO: Reemplazar estos datos de ejemplo por los del backend.
    val sensor = when (sector) {
        "Taller" -> "S-003"
        "Producción" -> "S-002"
        "Administración" -> "S-001"
        else -> "Sin sensor"
    }

    val nivelRuido = when (sector) {
        "Taller" -> "91 dBA"
        "Producción" -> "84 dBA"
        "Administración" -> "65 dBA"
        else -> "Sin datos"
    }

    val estado = when (sector) {
        "Taller" -> "Crítico"
        "Producción" -> "Atención"
        "Administración" -> "Normal"
        else -> "Sin datos"
    }
    val colorEstado = when (sector) {
        "Taller" -> Color(0xFFC62828)
        "Producción" -> Color(0xFF996000)
        "Administración" -> Color(0xFF2E7D32)
        else -> TextoSecundario
    }
    val colorFondo = when (sector) {
        "Taller" -> Color(0xFFFFE8EB)
        "Producción" -> Color(0xFFFFEFCB)
        "Administración" -> Color(0xFFE4F3E8)
        else -> Color.White
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
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
                text = sector,
                fontSize = 20.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sensor $sensor",
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Text(
                text = "ⓘ $estado",
                fontSize = 12.sp,
                color = colorEstado
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // TODO: Obtener mediciones y dosis reales del sector.
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
                    Text("Nivel actual", fontSize = 12.sp, color = TextoSecundario)
                    Text(
                        nivelRuido,
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
                        text = estado,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorEstado
                    )

                    Text(
                        text = "Dato de ejemplo",
                        fontSize = 10.sp,
                        color = TextoSecundario
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Mostrar el gráfico con mediciones históricas reales.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneSectorPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneSectorScreen(sector = "Taller" )
    }
}