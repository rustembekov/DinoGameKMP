package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import entity.GameState

@Composable
fun GameCanvas(
    state: GameState,
    onJump: () -> Unit,
    onRestart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(state.isGameOver) {
                detectTapGestures(onTap = {
                    if (state.isGameOver) {
                        onRestart()
                    } else {
                        onJump()
                    }
                })
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val groundY = size.height - GameConstants.GROUND_HEIGHT

            drawLine(
                color = Color.Black,
                start = Offset(0f, groundY),
                end = Offset(size.width, groundY),
                strokeWidth = 4f
            )

            drawRect(
                color = Color.Gray,
                topLeft = Offset(
                    x = state.dino.x,
                    y = groundY - state.dino.height - state.dino.y
                ),
                size = Size(state.dino.width, state.dino.height)
            )

            state.obstacles.forEach { obstacle ->
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(
                        x = obstacle.x,
                        y = groundY - obstacle.height
                    ),
                    size = Size(obstacle.width, obstacle.height)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Score: ${state.score}", fontSize = 20.sp, color = Color.Black)
            Text(text = "High Score: ${state.highScore}", fontSize = 16.sp, color = Color.DarkGray)
        }

        if (state.isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Game Over\nTap to Restart",
                    color = Color.Red,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
