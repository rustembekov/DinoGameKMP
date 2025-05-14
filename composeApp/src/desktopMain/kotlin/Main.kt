import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.sharedModule
import org.koin.core.context.startKoin
import ui.DinoGameScreenDesktop

fun main() = application {
    startKoin {
        modules(sharedModule)
    }

    Window(onCloseRequest = ::exitApplication, title = "Dino Game") {
        DinoGameScreenDesktop(viewModel = DesktopApp.viewModel)
    }
}
