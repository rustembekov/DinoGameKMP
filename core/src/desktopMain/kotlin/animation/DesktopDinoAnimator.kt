package animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class DesktopDinoAnimator : DinoAnimator {

    @Composable
    override fun Dino(modifier: Modifier) {
        val infiniteTransition = rememberInfiniteTransition("dinoRun")
        val animationValue by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "dinoRunning"
        )

        Canvas(modifier = modifier.fillMaxSize()) {
            drawDino(animationValue)
        }
    }

    private fun DrawScope.drawDino(animationValue: Float) {
        val width = size.width
        val height = size.height
        val bodyColor = Color(0xFF4F4F4F)
        val eyeColor = Color.White
        val pupilColor = Color.Black

        // Body
        drawRoundRect(
            color = bodyColor,
            topLeft = Offset(width * 0.1f, height * 0.1f),
            size = Size(width * 0.7f, height * 0.8f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.1f)
        )

        // Head
        drawRoundRect(
            color = bodyColor,
            topLeft = Offset(width * 0.65f, height * 0.05f),
            size = Size(width * 0.3f, height * 0.3f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.05f)
        )

        // Eye
        drawCircle(
            color = eyeColor,
            radius = width * 0.05f,
            center = Offset(width * 0.85f, height * 0.15f)
        )

        // Pupil
        drawCircle(
            color = pupilColor,
            radius = width * 0.025f,
            center = Offset(width * 0.86f, height * 0.15f)
        )

        // Legs
        val legMovement = animationValue * height * 0.2f

        // Front leg
        drawRoundRect(
            color = bodyColor,
            topLeft = Offset(width * 0.75f, height * 0.65f - legMovement),
            size = Size(width * 0.08f, height * 0.3f + legMovement),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.04f)
        )

        // Back leg
        drawRoundRect(
            color = bodyColor,
            topLeft = Offset(width * 0.25f, height * 0.65f + legMovement),
            size = Size(width * 0.08f, height * 0.3f - legMovement),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(width * 0.04f)
        )
    }
}
