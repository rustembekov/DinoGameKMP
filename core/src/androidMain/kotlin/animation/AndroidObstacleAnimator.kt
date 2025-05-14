package animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

import com.example.dino.core.R as AppR
import entity.ObstacleType

class AndroidObstacleAnimator : ObstacleAnimator {
    @Composable
    override fun Obstacle(modifier: Modifier, type: ObstacleType) {
        when (type) {
            ObstacleType.TREE -> {
                DotLottieAnimation(
                    source = DotLottieSource.Url("https://lottie.host/5ff24962-bbc0-44d2-a77c-0e5581082994/gcOArAua8y.lottie"),
                    autoplay = true,
                    loop = true,
                    speed = 3f,
                    useFrameInterpolation = false,
                    playMode = Mode.FORWARD,
                    modifier = modifier.background(Color.LightGray)
                )
            }

            ObstacleType.BIRD -> {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(AppR.raw.bird) // R.raw.bird must exist
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



//@Preview(showBackground = true)
//@Composable
//private fun ObstacleBirdPreview() {
//    val obstacleAnimator = AndroidObstacleAnimator()
//    Box(modifier = Modifier.size(100.dp)) {
//        obstacleAnimator.Obstacle(
//            modifier = Modifier,
//            type = ObstacleType.BIRD
//        )
//    }
//}

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