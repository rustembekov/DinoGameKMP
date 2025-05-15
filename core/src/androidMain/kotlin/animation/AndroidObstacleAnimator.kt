package animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.dino.core.R as AppR
import entity.ObstacleType

class AndroidObstacleAnimator : ObstacleAnimator {
    @Composable
    override fun Obstacle(modifier: Modifier, type: ObstacleType) {
        if (LocalInspectionMode.current) {
            val color = when (type) {
                ObstacleType.TREE -> Color(0xFF8B4513) // Brown for tree
                ObstacleType.BIRD -> Color(0xFF4682B4) // Blue for bird
            }
            Box(modifier = modifier.background(color))
            return
        }

        when (type) {
            ObstacleType.TREE -> {
                Box(
                    modifier = modifier.background(Color.Transparent),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(3f)
                    ) {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(AppR.raw.tree)
                        )
                        val progress by animateLottieCompositionAsState(
                            composition,
                            iterations = LottieConstants.IterateForever
                        )

                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = modifier
                        )
                    }
                }
            }

            ObstacleType.BIRD -> {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(AppR.raw.bird)
                )
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ObstacleBirdPreview() {
    val obstacleAnimator = AndroidObstacleAnimator()
    Box(modifier = Modifier.size(100.dp)) {
        obstacleAnimator.Obstacle(
            modifier = Modifier,
            type = ObstacleType.BIRD
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ObstacleTreePreview() {
    val obstacleAnimator = AndroidObstacleAnimator()
    Box(modifier = Modifier.size(100.dp)) {
        obstacleAnimator.Obstacle(
            modifier = Modifier.background(color = Color.Red),
            type = ObstacleType.TREE
        )
    }
}