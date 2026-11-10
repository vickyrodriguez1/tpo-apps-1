package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

// Modelo visual temporal.
// TODO: Reemplazar por los datos del endpoint /api/v1/dispositivos.
private data class SensorUi(
    val id: String,
    val sector: String,
    val conectado: Boolean,
    val ultimaActualizacion: String
)

// Pantalla real.
// No mostramos datos ficticios mientras esperamos integrar el endpoint.
@Composable
fun HygieneSensorsScreen() {
    HygieneSensorsContent(
        sensores = emptyList(),
        cargandoDatos = true
    )
}

// Contenido visual reutilizable.
@Composable
private fun HygieneSensorsContent(
    sensores: List<SensorUi>,
    cargandoDatos: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = "Sensores",
            fontSize = 20.sp,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE9D9FF),
                    RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = if (cargandoDatos) {
                    "Sensores pendientes de cargar"
                } else {
                    "${sensores.size} sensores instalados"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (cargandoDatos) {
                    "Información pendiente de conectar al backend"
                } else {
                    "✓ ${sensores.count { it.conectado }} conectados" +
                            "     ·     ${sensores.count { !it.conectado }} sin conexión"
                },
                fontSize = 12.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        sensores.forEach { sensor ->
            TarjetaSensor(
                id = sensor.id,
                sector = sensor.sector,
                conectado = sensor.conectado,
                ultimaActualizacion = sensor.ultimaActualizacion
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TarjetaSensor(
    id: String,
    sector: String,
    conectado: Boolean,
    ultimaActualizacion: String
) {
    Row(
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
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "$id · $sector",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = ultimaActualizacion,
                fontSize = 11.sp,
                color = TextoSecundario
            )
        }

        Text(
            text = if (conectado) "✓ Conectado" else "Sin conexión",
            modifier = Modifier
                .background(
                    color = if (conectado) {
                        Color(0xFFE4F3E8)
                    } else {
                        Color(0xFFF0E8FA)
                    },
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(
                    horizontal = 9.dp,
                    vertical = 5.dp
                ),
            fontSize = 10.sp,
            color = if (conectado) {
                Color(0xFF2E7D32)
            } else {
                TextoPrincipal
            }
        )
    }
}

// Datos ficticios exclusivamente para visualizar el diseño.
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HygieneSensorsPreview() {
    val sensoresEjemplo = listOf(
        SensorUi(
            "S-003",
            "Taller",
            true,
            "Actualizado hoy, 10:42"
        ),
        SensorUi(
            "S-002",
            "Producción",
            true,
            "Actualizado hoy, 10:42"
        ),
        SensorUi(
            "S-001",
            "Administración",
            true,
            "Actualizado hoy, 10:42"
        ),
        SensorUi(
            "S-004",
            "Depósito",
            false,
            "Último dato hoy, 08:14"
        )
    )

    SoundFriendlyWorkplaceTheme {
        HygieneSensorsContent(
            sensores = sensoresEjemplo
        )
    }
}



