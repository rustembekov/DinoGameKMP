package animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun DinoPreview() {
    val dinoAnimator = AndroidDinoAnimator()
    dinoAnimator.Dino(
        modifier = Modifier
    )
}
