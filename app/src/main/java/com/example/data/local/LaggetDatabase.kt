package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [
    BookmarkEntity::class,
    SpeakingHistoryEntity::class,
    UserLearningStats::class,
    DailyLoginEntity::class,
    UnlockedAchievementEntity::class,
    LanguageProficiencyEntity::class
  ],
  version = 5,
  exportSchema = false
)
abstract class LaggetDatabase : RoomDatabase() {
  abstract fun laggetDao(): LaggetDao

  companion object {
    @Volatile
    private var INSTANCE: LaggetDatabase? = null

    fun getDatabase(context: Context): LaggetDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          LaggetDatabase::class.java,
          "lagget_rit_database"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
