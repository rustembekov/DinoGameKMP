package animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import entity.ObstacleType

interface ObstacleAnimator {
    @Composable
    fun Obstacle(modifier: Modifier, type: ObstacleType)
}
