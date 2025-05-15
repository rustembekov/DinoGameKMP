package animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import entity.ObstacleType

class DesktopObstacleAnimator : ObstacleAnimator {

    @Composable
    override fun Obstacle(modifier: Modifier, type: ObstacleType) {
        val infiniteTransition = rememberInfiniteTransition("obstacleAnimation")
        val animationValue by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "obstacleMoving"
        )

        Canvas(modifier = modifier.fillMaxSize()) {
            when (type) {
                ObstacleType.TREE -> drawCactus()
                ObstacleType.BIRD -> drawBird(animationValue)
            }
        }
    }

    private fun DrawScope.drawCactus() {
        val width = size.width
        val height = size.height
        val cactusColor = Color(0xFF2E8B57) // Sea Green

        // Main stem
        drawRoundRect(
            color = cactusColor,
            topLeft = Offset(width * 0.4f, height * 0.2f),
            size = Size(width * 0.2f, height * 0.8f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        // Left branch
        drawRoundRect(
            color = cactusColor,
            topLeft = Offset(width * 0.2f, height * 0.35f),
            size = Size(width * 0.2f, height * 0.15f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        drawRoundRect(
            color = cactusColor,
            topLeft = Offset(width * 0.2f, height * 0.35f),
            size = Size(width * 0.1f, height * 0.3f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        // Right branch
        drawRoundRect(
            color = cactusColor,
            topLeft = Offset(width * 0.6f, height * 0.5f),
            size = Size(width * 0.2f, height * 0.15f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        drawRoundRect(
            color = cactusColor,
            topLeft = Offset(width * 0.7f, height * 0.5f),
            size = Size(width * 0.1f, height * 0.25f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        // Spikes
        for (i in 0 until 5) {
            drawLine(
                color = Color(0xFF004D00), // Dark Green
                start = Offset(width * 0.6f, height * (0.3f + i * 0.15f)),
                end = Offset(width * 0.7f, height * (0.25f + i * 0.15f)),
                strokeWidth = width * 0.01f
            )

            drawLine(
                color = Color(0xFF004D00), // Dark Green
                start = Offset(width * 0.4f, height * (0.3f + i * 0.15f)),
                end = Offset(width * 0.3f, height * (0.25f + i * 0.15f)),
                strokeWidth = width * 0.01f
            )
        }
    }

    private fun DrawScope.drawBird(animationValue: Float) {
        val width = size.width
        val height = size.height
        val birdColor = Color(0xFF4682B4) // Steel Blue
        val wingColor = Color(0xFF6CA6CD) // Sky Blue

        val wingAngle = 20f + (animationValue * 40f)

        // Body
        val bodyPath = Path().apply {
            moveTo(width * 0.3f, height * 0.5f)
            quadraticBezierTo(
                width * 0.5f, height * 0.3f,
                width * 0.7f, height * 0.5f
            )
            quadraticBezierTo(
                width * 0.8f, height * 0.6f,
                width * 0.85f, height * 0.5f
            )
            lineTo(width * 0.9f, height * 0.55f)
            quadraticBezierTo(
                width * 0.7f, height * 0.7f,
                width * 0.3f, height * 0.5f
            )
            close()
        }

        drawPath(
            path = bodyPath,
            color = birdColor
        )

        // Head and beak
        drawCircle(
            color = birdColor,
            radius = width * 0.12f,
            center = Offset(width * 0.85f, height * 0.45f)
        )

        val beakPath = Path().apply {
            moveTo(width * 0.95f, height * 0.4f)
            lineTo(width * 1.05f, height * 0.45f)
            lineTo(width * 0.95f, height * 0.5f)
            close()
        }

        drawPath(
            path = beakPath,
            color = Color(0xFFFF9912) // Dark Goldenrod
        )

        // Eye
        drawCircle(
            color = Color.White,
            radius = width * 0.03f,
            center = Offset(width * 0.88f, height * 0.42f)
        )

        drawCircle(
            color = Color.Black,
            radius = width * 0.015f,
            center = Offset(width * 0.89f, height * 0.42f)
        )

        // Wings
        rotate(degrees = -wingAngle, pivot = Offset(width * 0.5f, height * 0.55f)) {
            drawOval(
                color = wingColor,
                topLeft = Offset(width * 0.35f, height * 0.4f),
                size = Size(width * 0.3f, height * 0.1f),
                style = Stroke(width = width * 0.02f)
            )

            // Fill for wing
            drawOval(
                color = wingColor.copy(alpha = 0.5f),
                topLeft = Offset(width * 0.35f, height * 0.4f),
                size = Size(width * 0.3f, height * 0.1f)
            )
        }
    }
}