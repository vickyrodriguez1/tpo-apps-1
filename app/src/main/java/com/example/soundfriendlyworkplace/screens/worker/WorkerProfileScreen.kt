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

@Composable
fun WorkerProfileScreen(
    nombre: String = "Juan Pérez",
    rol: String = "Trabajador",
    sector: String = "Producción",
    onConnectionClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Perfil",
            fontSize = 20.sp,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta con el ícono del usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    LavandaClaro,
                    RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "♙",
                fontSize = 48.sp,
                color = VioletaPrincipal
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = nombre,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Información del trabajador
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                FilaPerfil(
                    etiqueta = "Rol",
                    valor = rol
                )

                Spacer(modifier = Modifier.height(18.dp))

                FilaPerfil(
                    etiqueta = "Sector",
                    valor = sector
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedButton(
            onClick = onConnectionClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Text(
                text = "Estado de conexión",
                color = VioletaPrincipal
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, BordeCampo)
        ) {
            Text(
                text = "Cerrar sesión",
                color = VioletaPrincipal
            )
        }
    }
}

@Composable
private fun FilaPerfil(
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            fontSize = 13.sp,
            color = TextoSecundario
        )

        Text(
            text = valor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkerProfilePreview() {
    SoundFriendlyWorkplaceTheme {
        WorkerProfileScreen()
    }
}

