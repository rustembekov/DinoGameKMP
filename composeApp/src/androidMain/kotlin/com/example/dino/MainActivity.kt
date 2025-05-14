package com.example.dino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.dino.ui.DinoGameScreenAndroid
import org.koin.android.ext.android.inject
import viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel : GameViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DinoGameScreenAndroid(viewModel)
        }
    }
}
