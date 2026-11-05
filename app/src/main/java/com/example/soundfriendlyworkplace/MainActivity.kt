package com.example.soundfriendlyworkplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundfriendlyworkplace.screens.SeguridadEHigiene.HygieneMainScreen
import com.example.soundfriendlyworkplace.screens.SeguridadEHigiene.HygieneSensorsScreen
import com.example.soundfriendlyworkplace.ui.theme.SoundFriendlyWorkplaceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SoundFriendlyWorkplaceTheme {
                HygieneMainScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    SoundFriendlyWorkplaceTheme {
        HygieneSensorsScreen()
    }
}