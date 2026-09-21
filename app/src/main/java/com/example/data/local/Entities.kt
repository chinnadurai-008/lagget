package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_insights")
data class BookmarkEntity(
  @PrimaryKey val insightId: String,
  val languageId: String,
  val categoryName: String,
  val title: String,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "speaking_history")
data class SpeakingHistoryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  val languageId: String,
  val exerciseId: String,
  val targetPhrase: String,
  val spokenText: String,
  val score: Int,
  val feedback: String,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_login_records")
data class DailyLoginEntity(
  @PrimaryKey val dateString: String, // Format: YYYY-MM-DD
  val timestamp: Long = System.currentTimeMillis(),
  val practiceCompleted: Boolean = false,
  val xpEarned: Int = 20,
  val languagePracticedId: String = "ta"
)

@Entity(tableName = "unlocked_achievements")
data class UnlockedAchievementEntity(
  @PrimaryKey val badgeId: String,
  val title: String,
  val description: String,
  val category: String,
  val iconEmoji: String,
  val unlockedDate: String, // YYYY-MM-DD
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_learning_stats")
data class UserLearningStats(
  @PrimaryKey val id: Int = 1,
  val streakDays: Int = 3,
  val bestStreakDays: Int = 7,
  val lastLoginDate: String = "",
  val totalActiveDays: Int = 3,
  val totalXp: Int = 240,
  val speakingSessionsCount: Int = 12,
  val insightsExploredCount: Int = 28,
  val wordsLearnedCount: Int = 105,
  val culturalModulesCompletedCount: Int = 3,
  val learnerName: String = "RIT Scholar",
  val department: String = "Computer Science & Engineering • RIT",
  // Daily Learning Goal Customization
  val dailyGoalType: String = "WORDS", // "WORDS" or "MINUTES"
  val dailyGoalTarget: Int = 10,       // e.g. 10 words or 15 minutes
  val dailyGoalProgress: Int = 4,      // progress achieved today
  val dailyGoalDate: String = ""       // Date of progress tracking
)

@Entity(tableName = "language_proficiencies")
data class LanguageProficiencyEntity(
  @PrimaryKey val languageId: String,
  val languageName: String,
  val assessedLevel: String, // "BEGINNER", "INTERMEDIATE", "ADVANCED"
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val assessedDate: String, // YYYY-MM-DD
  val timestamp: Long = System.currentTimeMillis()
)
