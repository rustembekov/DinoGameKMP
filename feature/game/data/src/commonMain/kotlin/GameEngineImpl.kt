import entity.GameState
import entity.Obstacle
import entity.ObstacleType

class GameEngineImpl : GameEngineRepository {
    private var obstacleSpawnTimer = 0
    private var difficultyMultiplier = 1.0f
    private var lastObstacleX = 0f

    override fun update(state: GameState): GameState {
        difficultyMultiplier = 1.0f + (state.score / 1500f).coerceAtMost(1.5f)

        val dino = state.dino
        val newVelocity = dino.velocityY + GameConstants.GRAVITY
        val newY = (dino.y + newVelocity).coerceAtLeast(0f)

        val updatedDino = dino.copy(
            y = newY,
            velocityY = if (newY <= 0f) 0f else newVelocity,
            isJumping = newY > 0f
        )

        val obstacleSpeed = GameConstants.OBSTACLE_SPEED * difficultyMultiplier
        val movedObstacles = state.obstacles.map {
            it.copy(x = it.x - obstacleSpeed)
        }.filter { it.x + it.width > 0 }

        val baseSpawnRate = GameConstants.INITIAL_SPAWN_DELAY
        val spawnRateReduction = (state.score / 500).coerceIn(0, 40)
        val spawnRate = (baseSpawnRate - spawnRateReduction).coerceAtLeast(60)

        val rightmostObstacleX = movedObstacles.maxOfOrNull { it.x + it.width } ?: 0f

        val shouldSpawn = obstacleSpawnTimer >= spawnRate &&
                movedObstacles.size < 3 &&
                (rightmostObstacleX == 0f || 800f - rightmostObstacleX >= GameConstants.MIN_OBSTACLE_DISTANCE)

        val nextObstacles = if (shouldSpawn) {
            obstacleSpawnTimer = 0
            lastObstacleX = 800f

            val type = if (state.score >= 700) {
                val birdChance = (state.score / 1500f).coerceIn(0.0f, 0.4f)
                if (Math.random() < birdChance) ObstacleType.BIRD else ObstacleType.TREE
            } else {
                ObstacleType.TREE
            }

            val obstacleY = if (type == ObstacleType.BIRD) {

                val heights = listOf(100f, 200f, 300f)
                heights.random()
            } else {
                0f
            }

            val newObstacle = Obstacle(
                x = 800f,
                y = obstacleY,
                width = GameConstants.OBSTACLE_WIDTH,
                height = GameConstants.OBSTACLE_HEIGHT,
                type = type
            )

            movedObstacles + newObstacle
        } else {
            obstacleSpawnTimer++
            movedObstacles
        }

        val collisionBufferX = updatedDino.width * 0.15f
        val collisionBufferY = updatedDino.height * 0.15f

        val collision = nextObstacles.any { obstacle ->
            val dinoLeft = updatedDino.x + collisionBufferX
            val dinoRight = updatedDino.x + updatedDino.width - collisionBufferX
            val dinoTop = updatedDino.y + collisionBufferY
            val dinoBottom = updatedDino.y + updatedDino.height - collisionBufferY

            val obstacleLeft = obstacle.x + collisionBufferX
            val obstacleRight = obstacle.x + obstacle.width - collisionBufferX
            val obstacleTop = obstacle.y + collisionBufferY
            val obstacleBottom = obstacle.y + obstacle.height - collisionBufferY

            val xOverlap = dinoRight > obstacleLeft && dinoLeft < obstacleRight
            val yOverlap = dinoBottom > obstacleTop && dinoTop < obstacleBottom

            xOverlap && yOverlap
        }

        val newScore = state.score + 1
        val newHighScore = if (newScore > state.highScore) newScore else state.highScore

        return if (collision) {
            state.copy(
                dino = updatedDino,
                obstacles = nextObstacles,
                score = newScore,
                highScore = newHighScore,
                isGameOver = true
            )
        } else {
            state.copy(
                dino = updatedDino,
                obstacles = nextObstacles,
                score = newScore,
                highScore = newHighScore
            )
        }
    }
}