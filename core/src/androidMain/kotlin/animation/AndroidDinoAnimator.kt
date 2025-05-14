package animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.dino.core.R as AppR

class AndroidDinoAnimator : DinoAnimator {
    @Composable
    override fun Dino(modifier: Modifier) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(AppR.raw.dino)
        )
        val progress by animateLottieCompositionAsState(composition)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier
                .background(Color.Green)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DinoPreview() {
    val dinoAnimator = AndroidDinoAnimator()
    Box(modifier = Modifier.size(100.dp)) {
        dinoAnimator.Dino(
            modifier = Modifier
        )
    }
}
