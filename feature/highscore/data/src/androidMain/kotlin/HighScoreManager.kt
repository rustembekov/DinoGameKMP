import android.content.Context

class HighScoreManager(context: Context) {
    private val prefs = context.getSharedPreferences("dino_prefs", Context.MODE_PRIVATE)

    fun saveHighScore(score: Int) {
        prefs.edit().putInt("high_score", score).apply()
    }

    fun loadHighScore(): Int {
        return prefs.getInt("high_score", 0)
    }
}
