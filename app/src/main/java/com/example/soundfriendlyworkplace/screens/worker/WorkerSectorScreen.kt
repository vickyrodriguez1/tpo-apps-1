package com.example.soundfriendlyworkplace.screens.worker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

private val AmarilloSector = Color(0xFFFFF0CC)
private val BordeAmarillo = Color(0xFFB68C48)
private val MarronSector = Color(0xFF946000)

@Composable
fun WorkerSectorScreen(
    sector: String = "Producción",
    codigoSector: String = "S-002",
    nivelRuido: Int = 84,
    dosis: Int = 82,
    onBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("‹", fontSize = 28.sp, color = TextoPrincipal)
            }

            Text(
                text = "Mi sector",
                fontSize = 20.sp,
                color = TextoPrincipal,
                modifier = Modifier.weight(1f)
            )

            TextButton(onClick = onRefresh) {
                Text("⟳", fontSize = 26.sp, color = VioletaPrincipal)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre del sector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$sector · $codigoSector",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextoPrincipal
            )

            Text(
                text = "⚠ Atención",
                fontSize = 12.sp,
                color = MarronSector
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Nivel ambiental
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, BordeAmarillo)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "$nivelRuido dBA",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nivel ambiental actual · 10:42",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Dosis acumulada
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = AmarilloSector
            ),
            border = BorderStroke(1.dp, BordeAmarillo)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "$dosis% de dosis acumulada",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { dosis.coerceIn(0, 100) / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = MarronSector,
                    trackColor = Color(0xFFD8C6DF)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Durante el turno del sector",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Momentos del turno",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Eventos del turno
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                MomentoTurno("10:42", "Última lectura · 84 dBA")

                Spacer(modifier = Modifier.height(18.dp))

                MomentoTurno("09:54", "Superó el 80% de dosis")

                Spacer(modifier = Modifier.height(18.dp))

                MomentoTurno("08:00", "Inicio del turno")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Text(
                text = "Consultar historial",
                color = VioletaPrincipal
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MomentoTurno(
    hora: String,
    descripcion: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hora,
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Text(
            text = descripcion,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextoPrincipal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerSectorPreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerSectorScreen()
    }
}
