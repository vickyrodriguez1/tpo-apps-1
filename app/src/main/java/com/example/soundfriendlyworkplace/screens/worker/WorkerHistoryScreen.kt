package com.example.soundfriendlyworkplace.screens.worker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.*

private val FondoAtencion = Color(0xFFFFF0CC)
private val FondoCritico = Color(0xFFFBE8ED)
private val FondoNormal = Color(0xFFE2F4EB)

private val ColorAtencion = Color(0xFF946000)
private val ColorCritico = Color(0xFFBD3030)
private val ColorNormal = Color(0xFF198754)

private data class RegistroSector(
    val fecha: String,
    val nivel: Int,
    val dosis: Int,
    val estado: String
)

@Composable
fun WorkerHistoryScreen(
    onRefresh: () -> Unit = {}
) {
    var periodo by rememberSaveable { mutableStateOf("Semana") }

    val registros = listOf(
        RegistroSector("Hoy", 84, 82, "Atención"),
        RegistroSector("Ayer", 91, 106, "Crítico"),
        RegistroSector("5 oct.", 88, 91, "Atención"),
        RegistroSector("4 oct.", 82, 68, "Normal")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Historial del sector",
                fontSize = 20.sp,
                color = TextoPrincipal
            )

            TextButton(onClick = onRefresh) {
                Text(
                    text = "⟳",
                    fontSize = 26.sp,
                    color = VioletaPrincipal
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Producción",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector Día / Semana
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Día", "Semana").forEachIndexed { index, opcion ->
                SegmentedButton(
                    selected = periodo == opcion,
                    onClick = { periodo = opcion },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = 2
                    ),
                    label = { Text(opcion) }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = if (periodo == "Semana")
                        "Detalle por jornada"
                    else
                        "Detalle del día",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(14.dp))

                val registrosVisibles =
                    if (periodo == "Día") registros.take(1)
                    else registros

                registrosVisibles.forEach { registro ->
                    RegistroHistorial(registro)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun RegistroHistorial(registro: RegistroSector) {
    val fondo = when (registro.estado) {
        "Normal" -> FondoNormal
        "Crítico" -> FondoCritico
        else -> FondoAtencion
    }

    val colorEstado = when (registro.estado) {
        "Normal" -> ColorNormal
        "Crítico" -> ColorCritico
        else -> ColorAtencion
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo, RoundedCornerShape(10.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = registro.fecha,
            fontSize = 13.sp,
            color = TextoPrincipal
        )

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${registro.nivel} dBA · ${registro.dosis}%",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                color = fondo,
                border = BorderStroke(1.dp, colorEstado),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(
                    text = registro.estado,
                    fontSize = 11.sp,
                    color = colorEstado,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 3.dp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerHistoryPreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerHistoryScreen()
    }
}
