import entity.GameState

interface GameEngineRepository {
    fun update(state: GameState): GameState
}