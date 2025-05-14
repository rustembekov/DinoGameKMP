package com.example.dino.ui

import GameController
import InputController
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import ui.GameCanvas
import viewmodel.GameIntent
import viewmodel.GameViewModelInterface

@Composable
fun DinoGameScreenAndroid(viewModel: GameViewModelInterface) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window
    val insetsController = window?.let { WindowCompat.getInsetsController(it, it.decorView) }

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            insetsController?.show(WindowInsetsCompat.Type.statusBars())
        } else {
            insetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }
    }

    val controller = remember { GameController(viewModel, InputController()) }

    LaunchedEffect(Unit) {
        controller.bindInputs()
        while (isActive) {
            viewModel.onIntent(GameIntent.Update)
            delay(16L)
        }
    }

    GameCanvas(
        state = state,
        onJump = { controller.onJump() },
        onRestart = { viewModel.onIntent(GameIntent.Reset) }
    )
}
