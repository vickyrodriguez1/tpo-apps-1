package com.example.soundfriendlyworkplace.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundfriendlyworkplace.ui.theme.*

@Composable
fun LoginScreen(
    onForgotPassword: () -> Unit = {},
    onLogin: (String, String) -> Unit = { _, _ -> }
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLavanda)
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = "Sound Friendly Workplace",
            fontSize = 20.sp,
            color = TextoPrincipal,
            modifier = Modifier.padding(top = 22.dp)
        )

        Spacer(modifier = Modifier.height(115.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .background(
                    LavandaClaro,
                    RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "♫",
                fontSize = 42.sp,
                color = VioletaPrincipal
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Bienvenido de nuevo",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ingresá para cuidar tu entorno de trabajo.",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Correo electrónico",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("example@example.com")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Contraseña",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = VioletaPrincipal,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onForgotPassword() }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLogin(correo, contrasena) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VioletaPrincipal
            )
        ) {
            Text(
                text = "Iniciar sesión",
                fontWeight = FontWeight.SemiBold,
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}

