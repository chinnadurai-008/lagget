package com.example.data.model

enum class GoalType(val label: String, val unit: String, val iconEmoji: String) {
  WORDS("Words per Day", "words", "📚"),
  MINUTES("Minutes per Day", "mins", "⏱️")
}

data class DailyLearningGoal(
  val goalType: GoalType = GoalType.WORDS,
  val targetValue: Int = 10, // e.g. 10 words or 15 minutes
  val currentProgress: Int = 4,
  val isCompleted: Boolean = false
) {
  val progressFraction: Float
    get() = if (targetValue <= 0) 0f else (currentProgress.toFloat() / targetValue.toFloat()).coerceIn(0f, 1f)
  
  val percentageText: String
    get() = "${(progressFraction * 100).toInt()}%"
}
