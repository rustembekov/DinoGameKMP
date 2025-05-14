package viewmodel

import JumpUseCase
import ResetGameUseCase
import UpdateGameUseCase
import entity.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(
    private val updateGame: UpdateGameUseCase,
    private val resetGame: ResetGameUseCase,
    private val jump: JumpUseCase
) : BaseViewModel(), GameViewModelInterface {
    private val _state = MutableStateFlow(GameState())
    override val state: StateFlow<GameState> = _state

    override fun onIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.Update -> {
                if (!_state.value.isGameOver) {
                    _state.value = updateGame(_state.value)
                }
            }
            is GameIntent.Reset -> {
                _state.value = resetGame(_state.value.highScore)
            }
            is GameIntent.Jump -> {
                _state.value = jump(_state.value)
            }
        }
    }
}


