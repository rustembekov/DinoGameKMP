package viewmodel

import entity.GameState
import kotlinx.coroutines.flow.StateFlow

interface GameViewModelInterface {
    val state: StateFlow<GameState>
    fun onIntent(intent: GameIntent)
}

sealed class GameIntent {
    data object Jump : GameIntent()
    data object Update : GameIntent()
    data object Reset : GameIntent()
}