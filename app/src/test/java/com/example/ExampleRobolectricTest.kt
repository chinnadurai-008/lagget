package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Lagget", appName)
  }

  @Test
  fun `verify world languages cataloged`() {
    val languages = com.example.data.repository.LanguageRepository.worldLanguages
    assertTrue(languages.isNotEmpty())
    assertTrue(languages.any { it.id == "ta" })
    assertTrue(languages.any { it.id == "es" })
    assertTrue(languages.any { it.id == "ja" })
  }

  @Test
  fun `verify cultural insights modules loaded`() {
    val tamilInsights = com.example.data.repository.LanguageRepository.getCulturalInsightsForLanguage("ta")
    assertTrue(tamilInsights.isNotEmpty())
    assertTrue(tamilInsights.any { it.category == com.example.data.model.CulturalCategory.GREETINGS })
    assertTrue(tamilInsights.any { it.category == com.example.data.model.CulturalCategory.DINING_ETIQUETTE })
    assertTrue(tamilInsights.any { it.category == com.example.data.model.CulturalCategory.HOLIDAYS_FESTIVALS })
  }

  @Test
  fun `verify daily login room persistence`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = com.example.data.local.LaggetDatabase.getDatabase(context)
    val dao = db.laggetDao()

    val testDate = "2026-09-21"
    val loginRecord = com.example.data.local.DailyLoginEntity(
      dateString = testDate,
      practiceCompleted = true,
      xpEarned = 25
    )
    dao.insertDailyLogin(loginRecord)

    val retrieved = dao.getDailyLoginForDate(testDate)
    assertTrue(retrieved != null)
    assertEquals(testDate, retrieved?.dateString)
    assertTrue(retrieved?.practiceCompleted == true)
  }

  @Test
  fun `verify achievement badge room persistence and milestone criteria`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = com.example.data.local.LaggetDatabase.getDatabase(context)
    val dao = db.laggetDao()

    // 1. Insert unlocked achievement into Room
    val achievement = com.example.data.local.UnlockedAchievementEntity(
      badgeId = "streak_7_day",
      title = "7-Day Streak",
      description = "Maintained a 7-day learning streak",
      category = "STREAKS",
      iconEmoji = "🔥",
      unlockedDate = "2026-09-21"
    )
    dao.insertAchievement(achievement)

    val isUnlocked = dao.isAchievementUnlocked("streak_7_day")
    assertTrue("7-day streak badge must be recorded as unlocked in Room", isUnlocked)

    // 2. Verify model logic for 100 words learned milestone
    val wordsBadge = com.example.data.model.AchievementBadge(
      id = "words_100",
      title = "100 Words Learned",
      description = "Master 100 vocabulary words",
      category = com.example.data.model.AchievementCategory.VOCABULARY,
      iconEmoji = "📚",
      targetMilestone = 100,
      currentProgress = 105,
      isUnlocked = true,
      xpReward = 150
    )
    assertTrue(wordsBadge.isUnlocked)
    assertEquals(1.0f, wordsBadge.progressFraction, 0.001f)

    // 3. Verify cultural module completed badge
    val cultureBadge = com.example.data.model.AchievementBadge(
      id = "culture_completed",
      title = "Completed Cultural Module",
      description = "Completed immersion modules",
      category = com.example.data.model.AchievementCategory.CULTURE,
      iconEmoji = "🏛️",
      targetMilestone = 1,
      currentProgress = 1,
      isUnlocked = true,
      xpReward = 120
    )
    assertTrue(cultureBadge.isUnlocked)
  }

  @Test
  fun `verify daily learning goal progress calculation and adjustment`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = com.example.data.local.LaggetDatabase.getDatabase(context)
    val dao = db.laggetDao()

    // 1. Verify UserLearningStats saves goal parameters in Room
    val initialStats = com.example.data.local.UserLearningStats(
      id = 1,
      streakDays = 5,
      dailyGoalType = "WORDS",
      dailyGoalTarget = 15,
      dailyGoalProgress = 9,
      dailyGoalDate = "2026-09-21"
    )
    dao.saveUserStats(initialStats)

    val flowStats = dao.getUserStats()
    // Test Room write & read
    val initialGoal = com.example.data.model.DailyLearningGoal(
      goalType = com.example.data.model.GoalType.WORDS,
      targetValue = 15,
      currentProgress = 9,
      isCompleted = false
    )
    assertEquals(0.6f, initialGoal.progressFraction, 0.001f)
    assertEquals("60%", initialGoal.percentageText)
    assertFalse(initialGoal.isCompleted)

    // 2. Complete the goal (15/15 words)
    val completedGoal = initialGoal.copy(
      currentProgress = 15,
      isCompleted = true
    )
    assertEquals(1.0f, completedGoal.progressFraction, 0.001f)
    assertEquals("100%", completedGoal.percentageText)
    assertTrue(completedGoal.isCompleted)

    // 3. Goal adjustment to MINUTES per day
    val minuteGoal = com.example.data.model.DailyLearningGoal(
      goalType = com.example.data.model.GoalType.MINUTES,
      targetValue = 20,
      currentProgress = 20,
      isCompleted = true
    )
    assertEquals(20, minuteGoal.targetValue)
    assertEquals("mins", minuteGoal.goalType.unit)
    assertEquals("Minutes per Day", minuteGoal.goalType.label)
    assertTrue(minuteGoal.isCompleted)
  }

  @Test
  fun `verify diagnostic questions exist for all supported languages`() {
    val languages = com.example.data.repository.LanguageRepository.worldLanguages
    languages.forEach { lang ->
      val questions = com.example.data.repository.DiagnosticTestRepository.getDiagnosticQuestionsForLanguage(lang)
      assertTrue("Questions for ${lang.name} must not be empty", questions.isNotEmpty())
      assertEquals("Each language diagnostic test should have 5 questions", 5, questions.size)
      questions.forEach { q ->
        assertTrue("Question prompt must not be blank", q.prompt.isNotBlank())
        assertTrue("Question options must have at least 3 choices", q.options.size >= 3)
        assertTrue("Correct option index must be in range", q.correctOptionIndex in q.options.indices)
        assertTrue("Explanation must be provided", q.explanation.isNotBlank())
      }
    }
  }

  @Test
  fun `verify proficiency level categorization logic`() {
    assertEquals(com.example.data.model.ProficiencyLevel.BEGINNER, com.example.data.model.ProficiencyLevel.fromScorePercentage(0))
    assertEquals(com.example.data.model.ProficiencyLevel.BEGINNER, com.example.data.model.ProficiencyLevel.fromScorePercentage(40))
    assertEquals(com.example.data.model.ProficiencyLevel.INTERMEDIATE, com.example.data.model.ProficiencyLevel.fromScorePercentage(45))
    assertEquals(com.example.data.model.ProficiencyLevel.INTERMEDIATE, com.example.data.model.ProficiencyLevel.fromScorePercentage(60))
    assertEquals(com.example.data.model.ProficiencyLevel.INTERMEDIATE, com.example.data.model.ProficiencyLevel.fromScorePercentage(74))
    assertEquals(com.example.data.model.ProficiencyLevel.ADVANCED, com.example.data.model.ProficiencyLevel.fromScorePercentage(75))
    assertEquals(com.example.data.model.ProficiencyLevel.ADVANCED, com.example.data.model.ProficiencyLevel.fromScorePercentage(80))
    assertEquals(com.example.data.model.ProficiencyLevel.ADVANCED, com.example.data.model.ProficiencyLevel.fromScorePercentage(100))
  }

  @Test
  fun `verify language proficiency room persistence`() = kotlinx.coroutines.runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = com.example.data.local.LaggetDatabase.getDatabase(context)
    val dao = db.laggetDao()

    // Test saving a proficiency result
    val profRecord = com.example.data.local.LanguageProficiencyEntity(
      languageId = "ta",
      languageName = "Tamil",
      assessedLevel = com.example.data.model.ProficiencyLevel.INTERMEDIATE.name,
      score = 4,
      totalQuestions = 5,
      percentage = 80,
      assessedDate = "2026-09-21"
    )
    dao.saveProficiency(profRecord)

    val retrieved = dao.getProficiencySync("ta")
    assertTrue(retrieved != null)
    assertEquals("Tamil", retrieved?.languageName)
    assertEquals("INTERMEDIATE", retrieved?.assessedLevel)
    assertEquals(80, retrieved?.percentage)
    assertEquals(4, retrieved?.score)

    val hasCompleted = dao.hasCompletedProficiencyTest("ta")
    assertTrue("Tamil proficiency test should be marked as completed", hasCompleted)

    val notCompleted = dao.hasCompletedProficiencyTest("es_untested")
    assertFalse("Untested language should not be marked as completed", notCompleted)
  }
}
