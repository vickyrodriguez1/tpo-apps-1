
package com.example.soundfriendlyworkplace.screens.SeguridadEHigiene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun HygieneSensorsScreen() {

    // TODO: Obtener los sensores y sus estados reales desde el backend.
    val sensores = listOf(
        Triple("S-003", "Taller", true),
        Triple("S-002", "Producción", true),
        Triple("S-001", "Administración", true),
        Triple("S-004", "Depósito", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
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
                text = "${sensores.size} sensores instalados",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "✓ ${sensores.count { it.third }} conectados" +
                        "     ·     ${sensores.count { !it.third }} sin conexión",
                fontSize = 12.sp,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        sensores.forEach { (id, sector, conectado) ->
            TarjetaSensor(
                id = id,
                sector = sector,
                conectado = conectado
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TarjetaSensor(
    id: String,
    sector: String,
    conectado: Boolean
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
                text = if (conectado) {
                    "Actualizado hoy, 10:42"
                } else {
                    "Último dato hoy, 08:14"
                },
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
                .padding(horizontal = 9.dp, vertical = 5.dp),
            fontSize = 10.sp,
            color = if (conectado) {
                Color(0xFF2E7D32)
            } else {
                TextoPrincipal
            }
        )
    }
}


