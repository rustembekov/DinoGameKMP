package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
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
import com.example.dino.core.R as AppR

@Composable
fun GameCanvasAndroid(
    state: GameState,
    onJump: () -> Unit,
    onRestart: () -> Unit
) {
    val dinoAnimator: DinoAnimator = remember { AndroidDinoAnimator() }
    val obstacleAnimator: ObstacleAnimator = remember { AndroidObstacleAnimator() }
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .pointerInput(state.isGameOver) {
                detectTapGestures(onTap = {
                    if (state.isGameOver) onRestart() else onJump()
                })
            }
    ) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()
        val groundY = screenHeight - state.groundHeight


        Canvas(modifier = Modifier.fillMaxSize()) {
            drawOval(
                color = Color(0xFFE0E0E0),
                topLeft = Offset(screenWidth * 0.1f, screenHeight * 0.15f),
                size = androidx.compose.ui.geometry.Size(screenWidth * 0.22f, screenHeight * 0.06f)
            )

            drawOval(
                color = Color(0xFFE0E0E0),
                topLeft = Offset(screenWidth * 0.6f, screenHeight * 0.1f),
                size = androidx.compose.ui.geometry.Size(screenWidth * 0.25f, screenHeight * 0.06f)
            )

            drawOval(
                color = Color(0xFFE0E0E0),
                topLeft = Offset(screenWidth * 0.4f, screenHeight * 0.25f),
                size = androidx.compose.ui.geometry.Size(screenWidth * 0.15f, screenHeight * 0.04f)
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color(0xFF424242),
                start = Offset(0f, groundY),
                end = Offset(size.width, groundY),
                strokeWidth = 4f
            )

            for (i in 0 until screenWidth.toInt() step 40) {
                drawLine(
                    color = Color(0xFF757575),
                    start = Offset(i.toFloat(), groundY + 2),
                    end = Offset(i.toFloat() + 15, groundY + 2),
                    strokeWidth = 2f
                )
            }

            for (i in 10 until screenWidth.toInt() step 120) {
                val detailWidth = (Math.random() * 10 + 5).toFloat()
                drawLine(
                    color = Color(0xFF9E9E9E),
                    start = Offset(i.toFloat(), groundY + 4),
                    end = Offset(i.toFloat() + detailWidth, groundY + 4),
                    strokeWidth = 1.5f
                )
            }
        }

        Box(
            modifier = Modifier
                .width(with(density) { state.dino.width.toDp() })
                .height(with(density) { state.dino.height.toDp() })
                .offset(
                    x = with(density) { state.dino.x.toDp() },
                    y = with(density) { (groundY - state.dino.height - state.dino.y).toDp() }
                )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                dinoAnimator.Dino(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        state.obstacles.forEach { obstacle ->
            Box(
                modifier = Modifier
                    .width(with(density) { obstacle.width.toDp() })
                    .height(with(density) { obstacle.height.toDp() })
                    .offset(
                        x = with(density) { obstacle.x.toDp() },
                        y = with(density) { (groundY - obstacle.height - obstacle.y).toDp() }
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    obstacleAnimator.Obstacle(
                        modifier = Modifier.fillMaxSize(),
                        type = obstacle.type
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(
                    id = AppR.string.score_text,
                    formatArgs = arrayOf(state.score)
                ),
                fontSize = 28.sp,
                color = Color(0xFF212121),
                fontWeight = FontWeight.Bold
            )
            Text(
                text  = stringResource(
                    id = AppR.string.high_score_text,
                    formatArgs = arrayOf(state.highScore)
                ),
                fontSize = 20.sp,
                color = Color(0xFF616161),
                fontWeight = FontWeight.Medium
            )
        }

        if (state.dino.isJumping) {
            val jumpHeightPercent = (state.dino.y / screenHeight * 100).toInt()
            Text(
                text = stringResource(
                    id = AppR.string.jump,
                    formatArgs = arrayOf(jumpHeightPercent)
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp, end = 24.dp),
                fontSize = 18.sp,
                color = Color(0xFF424242),
                fontWeight = FontWeight.Medium
            )
        }

        if (state.isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(AppR.string.game_over),
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(
                            id = AppR.string.score_text,
                            formatArgs = arrayOf(state.score)
                        ),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFF5722),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 36.dp, vertical = 18.dp)
                    ) {
                        Text(
                            text = stringResource(AppR.string.restart),
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameCanvasPreview() {
    val mockDino = Dino(
        x = 100f,
        y = 50f,
        velocityY = 10f,
        width = GameConstants.DINO_WIDTH,
        height = GameConstants.DINO_HEIGHT,
        isJumping = true
    )

    val mockObstacles = listOf(
        Obstacle(
            x = 300f,
            y = 150f,
            width = GameConstants.OBSTACLE_WIDTH,
            height = GameConstants.OBSTACLE_HEIGHT,
            type = ObstacleType.BIRD
        ),
        Obstacle(
            x = 700f,
            y = 0f,
            width = GameConstants.OBSTACLE_WIDTH,
            height = GameConstants.OBSTACLE_HEIGHT,
            type = ObstacleType.TREE
        )
    )

    val mockState = GameState(
        dino = mockDino,
        obstacles = mockObstacles,
        score = 42,
        highScore = 100,
        isGameOver = false,
        groundHeight = GameConstants.GROUND_HEIGHT
    )

    GameCanvasAndroid(
        state = mockState,
        onJump = {},
        onRestart = {}
    )
}