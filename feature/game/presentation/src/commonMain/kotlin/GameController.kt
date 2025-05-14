import viewmodel.GameIntent
import viewmodel.GameViewModelInterface

class GameController(
    private val viewModel: GameViewModelInterface,
    private val inputController: InputController
) {
    fun onJump() = viewModel.onIntent(GameIntent.Jump)

    fun bindInputs() {
        inputController.onJump = ::onJump
    }
}
