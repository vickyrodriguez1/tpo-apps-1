package com.example.soundfriendlyworkplace.screens.worker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.soundfriendlyworkplace.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.ui.theme.SoundFriendlyWorkplaceTheme

private val AmarilloTarjeta = Color(0xFFFFF0CC)
private val MarronProgreso = Color(0xFF966100)

@Composable
fun WorkerHomeScreen(
    nombre: String = "Juan",
    sector: String = "Producción",
    nivelRuido: Int = 84,
    dosis: Int = 82,
    onStatusClick: () -> Unit = {},
    onSectorClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mi inicio",
                fontSize = 20.sp,
                color = TextoPrincipal
            )

            Text(
                text = "⟳",
                fontSize = 28.sp,
                color = VioletaPrincipal,
                modifier = Modifier
                    .clickable { onRefresh() }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Hola, $nombre",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Este es el estado de tu sector asignado.",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Tarjeta principal del sector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AmarilloTarjeta
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, MarronProgreso
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sector,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = TextoPrincipal
                    )

                    Text(
                        text = "⚠ Atención",
                        fontSize = 12.sp,
                        color = MarronProgreso
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Actualizado: hoy, 10:42",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "$nivelRuido dBA",
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "Nivel ambiental",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }

                    Column {
                        Text(
                            text = "$dosis%",
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "Dosis del sector",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LinearProgressIndicator(
                    progress = { dosis.coerceIn(0, 100) / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = MarronProgreso,
                    trackColor = Color(0xFFD8C6DF)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Explicación del estado
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onStatusClick() },
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = LavandaClaro
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "¿Qué significa este estado?  ›",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tu sector alcanzó el umbral de Atención.",
                    fontSize = 13.sp,
                    color = TextoPrincipal
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onSectorClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VioletaPrincipal
            )
        ) {
            Text("Ver mi sector hoy", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, BordeCampo
            )
        ) {
            Text(
                text = "Consultar historial del sector",
                color = VioletaPrincipal
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Los sensores miden el ambiente del sector, no tu exposición personal.",
            fontSize = 12.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true
)
@Composable
fun WorkerHomePreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerHomeScreen()
    }
}