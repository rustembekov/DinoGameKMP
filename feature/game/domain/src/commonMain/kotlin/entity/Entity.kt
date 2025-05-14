package entity

data class Dino(
    var x: Float = 150f,
    var y: Float = 0f,
    var velocityY: Float = 0f,
    val width: Float = GameConstants.DINO_WIDTH,
    val height: Float = GameConstants.DINO_HEIGHT,
    var isJumping: Boolean = false
)

data class GameState(
    val dino: Dino = Dino(),
    val obstacles: List<Obstacle> = emptyList(),
    val score: Int = 0,
    val highScore: Int = 0,
    val isGameOver: Boolean = false,
    val groundHeight: Float = GameConstants.GROUND_HEIGHT
)

data class Obstacle(
    var x: Float,
    val y: Float = 0f,
    val width: Float = GameConstants.OBSTACLE_WIDTH,
    val height: Float = GameConstants.OBSTACLE_HEIGHT,
    val type: ObstacleType = ObstacleType.TREE
)

enum class ObstacleType {
    TREE,
    BIRD
}