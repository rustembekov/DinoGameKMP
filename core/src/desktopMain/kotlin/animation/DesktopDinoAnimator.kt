package animation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

class DesktopDinoAnimator : DinoAnimator {
    @Composable
    override fun Dino(modifier: Modifier) {
        Image(
            painter = painterResource("dino.gif"),
            contentDescription = "Dino",
            modifier = modifier
        )
    }
}