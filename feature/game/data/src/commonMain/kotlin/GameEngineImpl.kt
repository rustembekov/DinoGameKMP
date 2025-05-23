import entity.GameState
import entity.Obstacle
import entity.ObstacleType

class GameEngineImpl : GameEngineRepository {
    private var obstacleSpawnTimer = 0
    private var difficultyMultiplier = 1.0f
    private var screenWidth = 800f
    private val spawnOffset = 100f

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
        }.filter { it.x + it.width > -50 }

        val baseSpawnRate = GameConstants.INITIAL_SPAWN_DELAY
        val spawnRateReduction = (state.score / 500).coerceIn(0, 40)
        val spawnRate = (baseSpawnRate - spawnRateReduction).coerceAtLeast(60)

        val rightmostObstacle = movedObstacles.maxByOrNull { it.x }
        val rightmostObstacleX = rightmostObstacle?.x ?: 0f
        val minDistanceToSpawn = GameConstants.MIN_OBSTACLE_DISTANCE * (1f + difficultyMultiplier * 0.1f)

        val shouldSpawn = obstacleSpawnTimer >= spawnRate &&
                movedObstacles.size < 3 &&
                (rightmostObstacle == null || (screenWidth + spawnOffset) - rightmostObstacleX >= minDistanceToSpawn)

        val nextObstacles = if (shouldSpawn) {
            obstacleSpawnTimer = 0

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
                x = screenWidth + spawnOffset,
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

        val dinoCollisionBufferX = updatedDino.width * 0.15f
        val dinoCollisionBufferY = updatedDino.height * 0.15f

        val collision = nextObstacles.any { obstacle ->
            val dinoLeft = updatedDino.x + dinoCollisionBufferX
            val dinoRight = updatedDino.x + updatedDino.width - dinoCollisionBufferX
            val dinoTop = updatedDino.y + dinoCollisionBufferY
            val dinoBottom = updatedDino.y + updatedDino.height - dinoCollisionBufferY

            when (obstacle.type) {
                ObstacleType.TREE -> {
                    val collisionWidth = obstacle.width * GameConstants.TREE_COLLISION_WIDTH_FACTOR
                    val collisionHeight = obstacle.height * GameConstants.TREE_COLLISION_HEIGHT_FACTOR
                    val widthMargin = (obstacle.width - collisionWidth) / 2

                    val obstacleLeft = obstacle.x + widthMargin + GameConstants.TREE_COLLISION_X_OFFSET
                    val obstacleRight = obstacleLeft + collisionWidth
                    val obstacleTop = obstacle.y
                    val obstacleBottom = obstacleTop + collisionHeight

                    val xOverlap = dinoRight > obstacleLeft && dinoLeft < obstacleRight
                    val yOverlap = dinoBottom > obstacleTop && dinoTop < obstacleBottom

                    xOverlap && yOverlap
                }

                ObstacleType.BIRD -> {
                    val birdHeight = obstacle.y
                    val collisionWidth = obstacle.width * GameConstants.BIRD_COLLISION_WIDTH_FACTOR
                    val collisionHeight = obstacle.height * GameConstants.BIRD_COLLISION_HEIGHT_FACTOR
                    val widthMargin = (obstacle.width - collisionWidth) / 2

                    val obstacleLeft = obstacle.x + widthMargin
                    val obstacleRight = obstacleLeft + collisionWidth

                    val birdBottom = birdHeight + collisionHeight

                    val xOverlap = dinoRight > obstacleLeft && dinoLeft < obstacleRight

                    val yOverlap = when (birdHeight.toInt()) {
                        100 -> dinoBottom > birdHeight && dinoTop < birdBottom

                        200 -> {
                            val dinoJumpHeight = updatedDino.y
                            dinoJumpHeight in 100f..300f && dinoBottom > birdHeight && dinoTop < birdBottom
                        }

                        300 -> {
                            val dinoJumpHeight = updatedDino.y
                            dinoJumpHeight > 200f && dinoBottom > birdHeight && dinoTop < birdBottom
                        }

                        else -> dinoBottom > birdHeight && dinoTop < birdBottom
                    }

                    xOverlap && yOverlap
                }
            }
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