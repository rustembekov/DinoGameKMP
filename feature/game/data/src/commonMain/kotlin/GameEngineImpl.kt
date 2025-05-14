import entity.GameState
import entity.Obstacle
import entity.ObstacleType

class GameEngineImpl : GameEngineRepository {
    private var obstacleSpawnTimer = 0

    override fun update(state: GameState): GameState {
        val dino = state.dino
        val newVelocity = dino.velocityY + GameConstants.GRAVITY
        val newY = (dino.y + newVelocity).coerceAtLeast(0f)

        val updatedDino = dino.copy(
            y = newY,
            velocityY = if (newY <= 0f) 0f else newVelocity,
            isJumping = newY > 0f
        )

        val movedObstacles = state.obstacles.map {
            it.copy(x = it.x - GameConstants.OBSTACLE_SPEED)
        }.filter { it.x + it.width > 0 }

        val shouldSpawn = obstacleSpawnTimer >= 60
        val nextObstacles = if (shouldSpawn) {
            obstacleSpawnTimer = 0

            val type = if (state.score >= 700) {
                // 2/3 chance to spawn TREE
                if ((1..3).random() <= 2) ObstacleType.TREE else ObstacleType.BIRD
            } else {
                ObstacleType.TREE
            }

            val newObstacle = Obstacle(
                x = 800f,
                width = GameConstants.OBSTACLE_WIDTH,
                height = GameConstants.OBSTACLE_HEIGHT,
                type = type
            )

            movedObstacles + newObstacle
        } else {
            obstacleSpawnTimer++
            movedObstacles
        }

        val collision = nextObstacles.any { obstacle ->
            val dinoBottom = GameConstants.GROUND_HEIGHT + updatedDino.y
            val obstacleBottom = GameConstants.GROUND_HEIGHT

            val xCollide = updatedDino.x < obstacle.x + obstacle.width &&
                    updatedDino.x + updatedDino.width > obstacle.x
            val yCollide = dinoBottom < obstacleBottom + obstacle.height

            xCollide && yCollide
        }

        return if (collision) {
            state.copy(isGameOver = true)
        } else {
            state.copy(
                dino = updatedDino,
                obstacles = nextObstacles,
                score = state.score + 1
            )
        }
    }
}

