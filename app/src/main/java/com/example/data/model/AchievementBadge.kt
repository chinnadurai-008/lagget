package com.example.data.model

enum class AchievementCategory(val displayName: String, val iconEmoji: String) {
  ALL("All Badges", "🌟"),
  STREAKS("Streaks", "🔥"),
  VOCABULARY("Vocabulary", "📚"),
  CULTURE("Culture", "🏛️"),
  SPEAKING("Speaking", "🎙️")
}

enum class BadgeTier(val label: String, val colorHex: Long) {
  BRONZE("Bronze", 0xFFCD7F32),
  SILVER("Silver", 0xFFC0C0C0),
  GOLD("Gold", 0xFFFFD700),
  PLATINUM("Platinum", 0xFF00E5FF)
}

data class AchievementBadge(
  val id: String,
  val title: String,
  val description: String,
  val category: AchievementCategory,
  val iconEmoji: String,
  val targetMilestone: Int,
  val currentProgress: Int,
  val isUnlocked: Boolean,
  val unlockedDate: String? = null,
  val xpReward: Int,
  val tier: BadgeTier = BadgeTier.GOLD,
  val requirementHint: String = ""
) {
  val progressFraction: Float
    get() = if (targetMilestone <= 0) 0f else (currentProgress.toFloat() / targetMilestone.toFloat()).coerceIn(0f, 1f)
}
