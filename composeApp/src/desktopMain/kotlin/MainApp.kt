import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DesktopApp : KoinComponent {
    val viewModel: viewmodel.GameViewModel by inject()
}
