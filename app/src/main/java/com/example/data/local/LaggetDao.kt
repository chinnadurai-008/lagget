package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LaggetDao {
  @Query("SELECT * FROM bookmarked_insights ORDER BY timestamp DESC")
  fun getAllBookmarks(): Flow<List<BookmarkEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_insights WHERE insightId = :insightId)")
  fun isBookmarked(insightId: String): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertBookmark(bookmark: BookmarkEntity)

  @Query("DELETE FROM bookmarked_insights WHERE insightId = :insightId")
  suspend fun deleteBookmark(insightId: String)

  @Query("SELECT * FROM speaking_history ORDER BY timestamp DESC LIMIT 20")
  fun getRecentSpeakingHistory(): Flow<List<SpeakingHistoryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSpeakingAttempt(history: SpeakingHistoryEntity)

  @Query("SELECT * FROM user_learning_stats WHERE id = 1")
  fun getUserStats(): Flow<UserLearningStats?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveUserStats(stats: UserLearningStats)

  @Query("SELECT * FROM daily_login_records ORDER BY dateString DESC")
  fun getAllDailyLogins(): Flow<List<DailyLoginEntity>>

  @Query("SELECT * FROM daily_login_records ORDER BY dateString DESC LIMIT 7")
  fun getRecentDailyLogins(): Flow<List<DailyLoginEntity>>

  @Query("SELECT * FROM daily_login_records WHERE dateString = :dateString LIMIT 1")
  suspend fun getDailyLoginForDate(dateString: String): DailyLoginEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDailyLogin(record: DailyLoginEntity)

  @Query("SELECT COUNT(*) FROM daily_login_records")
  suspend fun getTotalLoginDaysCount(): Int

  @Query("SELECT * FROM unlocked_achievements ORDER BY timestamp DESC")
  fun getAllUnlockedAchievements(): Flow<List<UnlockedAchievementEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM unlocked_achievements WHERE badgeId = :badgeId)")
  suspend fun isAchievementUnlocked(badgeId: String): Boolean

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAchievement(achievement: UnlockedAchievementEntity)

  @Query("SELECT COUNT(*) FROM unlocked_achievements")
  fun getUnlockedAchievementsCount(): Flow<Int>

  @Query("SELECT * FROM language_proficiencies")
  fun getAllProficiencies(): Flow<List<LanguageProficiencyEntity>>

  @Query("SELECT * FROM language_proficiencies WHERE languageId = :languageId LIMIT 1")
  fun getProficiencyForLanguage(languageId: String): Flow<LanguageProficiencyEntity?>

  @Query("SELECT * FROM language_proficiencies WHERE languageId = :languageId LIMIT 1")
  suspend fun getProficiencySync(languageId: String): LanguageProficiencyEntity?

  @Query("SELECT EXISTS(SELECT 1 FROM language_proficiencies WHERE languageId = :languageId)")
  suspend fun hasCompletedProficiencyTest(languageId: String): Boolean

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveProficiency(entity: LanguageProficiencyEntity)

  @Query("DELETE FROM language_proficiencies WHERE languageId = :languageId")
  suspend fun deleteProficiency(languageId: String)
}
