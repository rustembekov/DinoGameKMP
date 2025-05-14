package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import animation.DinoAnimator
import animation.AndroidDinoAnimator
import animation.AndroidObstacleAnimator
import animation.ObstacleAnimator
import entity.Dino
import entity.GameState
import entity.Obstacle
import entity.ObstacleType

@Composable
fun GameCanvas(
    state: GameState,
    onJump: () -> Unit,
    onRestart: () -> Unit
) {
    val dinoAnimator: DinoAnimator = remember { AndroidDinoAnimator() }
    val obstacleAnimator: ObstacleAnimator = remember { AndroidObstacleAnimator() }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(state.isGameOver) {
                detectTapGestures(onTap = {
                    if (state.isGameOver) onRestart() else onJump()
                })
            }
    ) {
        val screenHeight = constraints.maxHeight.toFloat()
        val groundY = screenHeight - state.groundHeight

        // Draw ground
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, groundY),
                end = Offset(size.width, groundY),
                strokeWidth = 4f
            )
        }

        // Draw Dino
        val dinoHeightDp = state.dino.height.dp
        dinoAnimator.Dino(
            modifier = Modifier
                .size(width = state.dino.width.dp, height = dinoHeightDp)
                .offset(
                    x = state.dino.x.dp,
                    y = (groundY - state.dino.height - state.dino.y).dp // align Dino bottom to ground
                )
        )

        // Draw Obstacles
        state.obstacles.forEach { obstacle ->
            obstacleAnimator.Obstacle(
                modifier = Modifier
                    .size(width = obstacle.width.dp, height = obstacle.height.dp)
                    .offset(
                        x = obstacle.x.dp,
                        y = (groundY - obstacle.height - obstacle.y).dp
                    ),
                type = obstacle.type
            )
        }

        // Scoreboard
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Score: ${state.score}", fontSize = 20.sp, color = Color.Black)
            Text("High Score: ${state.highScore}", fontSize = 16.sp, color = Color.DarkGray)
        }

        // Game Over UI
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


@Preview(showBackground = true)
@Composable
fun GameCanvasPreview() {
    val mockDino = Dino(
        x = 100f,
        y = 0f,
        velocityY = 0f,
        isJumping = false
    )

    val mockObstacles = listOf(
        Obstacle(x = 300f, y = 0f, type = ObstacleType.BIRD),
        Obstacle(x = 600f, y = 0f, type = ObstacleType.TREE)
    )

    val mockState = GameState(
        dino = mockDino,
        obstacles = mockObstacles,
        score = 42,
        highScore = 100,
        isGameOver = false
    )

    GameCanvas(
        state = mockState,
        onJump = {},
        onRestart = {}
    )
}