import entity.GameState

class UpdateGameUseCase(private val gameEngineRepository: GameEngineRepository) {
    operator fun invoke(state: GameState): GameState {
        return gameEngineRepository.update(state)
    }
}

class ResetGameUseCase {
    operator fun invoke(highScore: Int): GameState {
        return GameState(highScore = highScore)
    }
}

class JumpUseCase {
    operator fun invoke(state: GameState): GameState {
        return if (!state.dino.isJumping) {
            state.copy(
                dino = state.dino.copy(
                    velocityY = GameConstants.JUMP_VELOCITY,
                    isJumping = true
                )
            )
        } else state
    }
}
