package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.GeminiApiService
import com.example.data.local.BookmarkEntity
import com.example.data.local.DailyLoginEntity
import com.example.data.local.LaggetDatabase
import com.example.data.local.LanguageProficiencyEntity
import com.example.data.local.SpeakingHistoryEntity
import com.example.data.local.UnlockedAchievementEntity
import com.example.data.local.UserLearningStats
import com.example.data.model.AchievementBadge
import com.example.data.model.AchievementCategory
import com.example.data.model.BadgeTier
import com.example.data.model.CulturalCategory
import com.example.data.model.CulturalInsightItem
import com.example.data.model.DailyLearningGoal
import com.example.data.model.DiagnosticQuestion
import com.example.data.model.DiagnosticTestResult
import com.example.data.model.DiagnosticTestState
import com.example.data.model.GoalType
import com.example.data.model.Language
import com.example.data.model.ProficiencyLevel
import com.example.data.model.SpeakingEvaluation
import com.example.data.model.SpeakingExercise
import com.example.data.model.WorldRegion
import com.example.data.repository.DiagnosticTestRepository
import com.example.data.repository.LanguageRepository
import com.example.speech.SpeechManager
import com.example.ui.components.DayStreakInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class LaggetNavDestination(val label: String, val icon: String) {
  LANGUAGES("Languages", "🌐"),
  CULTURE("Culture", "🏛️"),
  SPEAKING("Speaking", "🎙️"),
  PROFILE("Profile", "🏆"),
  HERITAGE("RIT Heritage", "🎓")
}

class LaggetViewModel(application: Application) : AndroidViewModel(application) {

  private val dao = LaggetDatabase.getDatabase(application).laggetDao()
  private val geminiService = GeminiApiService()
  val speechManager = SpeechManager(application)

  private val _currentDestination = MutableStateFlow(LaggetNavDestination.LANGUAGES)
  val currentDestination: StateFlow<LaggetNavDestination> = _currentDestination.asStateFlow()

  // Language state
  private val _selectedLanguage = MutableStateFlow(LanguageRepository.getLanguageById("ta"))
  val selectedLanguage: StateFlow<Language> = _selectedLanguage.asStateFlow()

  private val _selectedRegion = MutableStateFlow(WorldRegion.ALL)
  val selectedRegion: StateFlow<WorldRegion> = _selectedRegion.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  val filteredLanguages: StateFlow<List<Language>> = combine(
    _selectedRegion,
    _searchQuery
  ) { region, query ->
    LanguageRepository.worldLanguages.filter { lang ->
      val matchesRegion = (region == WorldRegion.ALL || lang.region == region.displayName)
      val matchesQuery = query.isBlank() ||
          lang.name.contains(query, ignoreCase = true) ||
          lang.nativeName.contains(query, ignoreCase = true) ||
          lang.scriptName.contains(query, ignoreCase = true)
      matchesRegion && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.Lazily, LanguageRepository.worldLanguages)

  // Cultural Insights State
  private val _selectedCulturalCategory = MutableStateFlow<CulturalCategory?>(null)
  val selectedCulturalCategory: StateFlow<CulturalCategory?> = _selectedCulturalCategory.asStateFlow()

  private val _customAiInsights = MutableStateFlow<List<CulturalInsightItem>>(emptyList())
  val customAiInsights: StateFlow<List<CulturalInsightItem>> = _customAiInsights.asStateFlow()

  private val _isGeneratingInsight = MutableStateFlow(false)
  val isGeneratingInsight: StateFlow<Boolean> = _isGeneratingInsight.asStateFlow()

  private val _insightStatusMessage = MutableStateFlow<String?>(null)
  val insightStatusMessage: StateFlow<String?> = _insightStatusMessage.asStateFlow()

  val activeCulturalInsights: StateFlow<List<CulturalInsightItem>> = combine(
    _selectedLanguage,
    _selectedCulturalCategory,
    _customAiInsights
  ) { lang, category, customList ->
    val repoInsights = LanguageRepository.getCulturalInsightsForLanguage(lang.id)
    val combined = customList.filter { it.languageId == lang.id } + repoInsights
    if (category == null) combined else combined.filter { it.category == category }
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  // Bookmarks
  val bookmarkedIds: StateFlow<Set<String>> = dao.getAllBookmarks().combine(
    MutableStateFlow(Unit)
  ) { bookmarks, _ ->
    bookmarks.map { it.insightId }.toSet()
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

  // AI Speaking Practice State
  private val _selectedExercise = MutableStateFlow<SpeakingExercise?>(null)
  val selectedExercise: StateFlow<SpeakingExercise?> = _selectedExercise.asStateFlow()

  private val _spokenTranscript = MutableStateFlow("")
  val spokenTranscript: StateFlow<String> = _spokenTranscript.asStateFlow()

  private val _isEvaluatingSpeech = MutableStateFlow(false)
  val isEvaluatingSpeech: StateFlow<Boolean> = _isEvaluatingSpeech.asStateFlow()

  private val _speakingEvaluation = MutableStateFlow<SpeakingEvaluation?>(null)
  val speakingEvaluation: StateFlow<SpeakingEvaluation?> = _speakingEvaluation.asStateFlow()

  val recentSpeakingHistory: StateFlow<List<SpeakingHistoryEntity>> = dao.getRecentSpeakingHistory()
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val userStats: StateFlow<UserLearningStats> = dao.getUserStats().combine(
    MutableStateFlow(Unit)
  ) { stats, _ ->
    stats ?: UserLearningStats()
  }.stateIn(viewModelScope, SharingStarted.Lazily, UserLearningStats())

  // Daily Learning Goal StateFlow (Calculates progress based on active goal type & today's activities)
  val dailyLearningGoal: StateFlow<DailyLearningGoal> = userStats.map { stats ->
    val type = try {
      GoalType.valueOf(stats.dailyGoalType)
    } catch (e: Exception) {
      GoalType.WORDS
    }
    val target = stats.dailyGoalTarget.coerceAtLeast(1)
    val progress = stats.dailyGoalProgress
    DailyLearningGoal(
      goalType = type,
      targetValue = target,
      currentProgress = progress,
      isCompleted = progress >= target
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, DailyLearningGoal())

  // Daily Login Streak Tracking (Room)
  val allDailyLogins: StateFlow<List<DailyLoginEntity>> = dao.getAllDailyLogins()
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val weeklyStreakList: StateFlow<List<DayStreakInfo>> = allDailyLogins.map { logins ->
    calculateWeeklyStreak(logins)
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val isTodayPracticed: StateFlow<Boolean> = allDailyLogins.map { logins ->
    val todayStr = getTodayDateString()
    logins.any { it.dateString == todayStr && it.practiceCompleted }
  }.stateIn(viewModelScope, SharingStarted.Lazily, false)

  // Unlocked Achievements (Room persistence)
  val unlockedAchievements: StateFlow<List<UnlockedAchievementEntity>> = dao.getAllUnlockedAchievements()
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  private val _selectedBadgeCategory = MutableStateFlow(AchievementCategory.ALL)
  val selectedBadgeCategory: StateFlow<AchievementCategory> = _selectedBadgeCategory.asStateFlow()

  private val _recentlyUnlockedBadge = MutableStateFlow<AchievementBadge?>(null)
  val recentlyUnlockedBadge: StateFlow<AchievementBadge?> = _recentlyUnlockedBadge.asStateFlow()

  // Language Proficiencies stored in Room (maps languageId -> LanguageProficiencyEntity)
  val allProficiencies: StateFlow<Map<String, LanguageProficiencyEntity>> = dao.getAllProficiencies().map { list ->
    list.associateBy { it.languageId }
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

  // Diagnostic Test State for Assessing Level
  private val _diagnosticTestState = MutableStateFlow<DiagnosticTestState?>(null)
  val diagnosticTestState: StateFlow<DiagnosticTestState?> = _diagnosticTestState.asStateFlow()

  // Dynamic milestone badge mapping combining Room stats, unlocked status & progress
  val allMilestoneBadges: StateFlow<List<AchievementBadge>> = combine(
    userStats,
    unlockedAchievements,
    recentSpeakingHistory
  ) { stats, unlockedList, speakingHist ->
    val unlockedMap = unlockedList.associateBy { it.badgeId }
    val bestStreak = maxOf(stats.streakDays, stats.bestStreakDays)
    val maxSpeechScore = speakingHist.maxOfOrNull { it.score } ?: 0

    listOf(
      AchievementBadge(
        id = "streak_7_day",
        title = "7-Day Streak",
        description = "Maintain a consistent daily language learning streak for 7 consecutive days",
        category = AchievementCategory.STREAKS,
        iconEmoji = "🔥",
        targetMilestone = 7,
        currentProgress = bestStreak,
        isUnlocked = unlockedMap.containsKey("streak_7_day") || bestStreak >= 7,
        unlockedDate = unlockedMap["streak_7_day"]?.unlockedDate ?: if (bestStreak >= 7) "Active Milestone" else null,
        xpReward = 100,
        tier = BadgeTier.GOLD,
        requirementHint = "Practice at least 1 phrase every day for a full week"
      ),
      AchievementBadge(
        id = "words_100",
        title = "100 Words Learned",
        description = "Master and practice 100 vocabulary terms across diverse world languages",
        category = AchievementCategory.VOCABULARY,
        iconEmoji = "📚",
        targetMilestone = 100,
        currentProgress = stats.wordsLearnedCount,
        isUnlocked = unlockedMap.containsKey("words_100") || stats.wordsLearnedCount >= 100,
        unlockedDate = unlockedMap["words_100"]?.unlockedDate ?: if (stats.wordsLearnedCount >= 100) "Active Milestone" else null,
        xpReward = 150,
        tier = BadgeTier.PLATINUM,
        requirementHint = "Complete speaking and cultural vocabulary exercises"
      ),
      AchievementBadge(
        id = "culture_completed",
        title = "Completed Cultural Module",
        description = "Complete cultural immersion modules in etiquette, dining, or traditions",
        category = AchievementCategory.CULTURE,
        iconEmoji = "🏛️",
        targetMilestone = 1,
        currentProgress = stats.culturalModulesCompletedCount,
        isUnlocked = unlockedMap.containsKey("culture_completed") || stats.culturalModulesCompletedCount >= 1,
        unlockedDate = unlockedMap["culture_completed"]?.unlockedDate ?: if (stats.culturalModulesCompletedCount >= 1) "Active Milestone" else null,
        xpReward = 120,
        tier = BadgeTier.GOLD,
        requirementHint = "Explore all insights in Greetings, Dining, or Holiday modules"
      ),
      AchievementBadge(
        id = "streak_3_day",
        title = "3-Day Habit Builder",
        description = "Form a consistent language study routine with a 3-day active streak",
        category = AchievementCategory.STREAKS,
        iconEmoji = "⚡",
        targetMilestone = 3,
        currentProgress = bestStreak,
        isUnlocked = unlockedMap.containsKey("streak_3_day") || bestStreak >= 3,
        unlockedDate = unlockedMap["streak_3_day"]?.unlockedDate ?: if (bestStreak >= 3) "Active Milestone" else null,
        xpReward = 50,
        tier = BadgeTier.BRONZE,
        requirementHint = "Check in and practice 3 days in a row"
      ),
      AchievementBadge(
        id = "speech_master_90",
        title = "Oratory Maestro",
        description = "Achieve an AI pronunciation evaluation score of 90% or above",
        category = AchievementCategory.SPEAKING,
        iconEmoji = "🎙️",
        targetMilestone = 90,
        currentProgress = maxSpeechScore,
        isUnlocked = unlockedMap.containsKey("speech_master_90") || maxSpeechScore >= 90,
        unlockedDate = unlockedMap["speech_master_90"]?.unlockedDate ?: if (maxSpeechScore >= 90) "Active Milestone" else null,
        xpReward = 100,
        tier = BadgeTier.SILVER,
        requirementHint = "Practice phrasing with clean tone, rhythm, and clarity"
      ),
      AchievementBadge(
        id = "culture_insights_25",
        title = "Global Anthropologist",
        description = "Discover and study 25+ real-time cultural nuances, etiquette, and idioms",
        category = AchievementCategory.CULTURE,
        iconEmoji = "🌍",
        targetMilestone = 25,
        currentProgress = stats.insightsExploredCount,
        isUnlocked = unlockedMap.containsKey("culture_insights_25") || stats.insightsExploredCount >= 25,
        unlockedDate = unlockedMap["culture_insights_25"]?.unlockedDate ?: if (stats.insightsExploredCount >= 25) "Active Milestone" else null,
        xpReward = 100,
        tier = BadgeTier.SILVER,
        requirementHint = "Browse greetings, idioms, and etiquette across regions"
      ),
      AchievementBadge(
        id = "tamil_sangam",
        title = "Sangam Heritage Scholar",
        description = "Honor RIT Autonomous Tamil classical legacy with native pronunciation",
        category = AchievementCategory.CULTURE,
        iconEmoji = "🪔",
        targetMilestone = 1,
        currentProgress = if (speakingHist.any { it.languageId == "ta" } || stats.insightsExploredCount > 0) 1 else 0,
        isUnlocked = unlockedMap.containsKey("tamil_sangam") || speakingHist.any { it.languageId == "ta" } || stats.insightsExploredCount > 0,
        unlockedDate = unlockedMap["tamil_sangam"]?.unlockedDate ?: "Active Milestone",
        xpReward = 80,
        tier = BadgeTier.GOLD,
        requirementHint = "Practice Tamil classical greetings or Thirukkural heritage"
      ),
      AchievementBadge(
        id = "speaking_10_sessions",
        title = "Voice Virtuoso",
        description = "Complete 10 interactive AI voice practice sessions",
        category = AchievementCategory.SPEAKING,
        iconEmoji = "🗣️",
        targetMilestone = 10,
        currentProgress = stats.speakingSessionsCount,
        isUnlocked = unlockedMap.containsKey("speaking_10_sessions") || stats.speakingSessionsCount >= 10,
        unlockedDate = unlockedMap["speaking_10_sessions"]?.unlockedDate ?: if (stats.speakingSessionsCount >= 10) "Active Milestone" else null,
        xpReward = 90,
        tier = BadgeTier.BRONZE,
        requirementHint = "Engage in voice dialogue with Gemini speech evaluator"
      )
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val filteredBadges: StateFlow<List<AchievementBadge>> = combine(
    allMilestoneBadges,
    selectedBadgeCategory
  ) { badges, category ->
    if (category == AchievementCategory.ALL) {
      badges
    } else {
      badges.filter { it.category == category }
    }
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  init {
    // Select initial exercise for default language
    selectLanguage(LanguageRepository.getLanguageById("ta"))
    // Track daily login streak in Room
    recordDailyLoginAndCalculateStreak()
    // Sync achievement unlocks into Room
    checkAndPersistUnlockedBadges()
  }

  private fun getTodayDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
  }

  private fun getYesterdayDateString(): String {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_YEAR, -1)
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.time)
  }

  fun recordDailyLoginAndCalculateStreak() {
    viewModelScope.launch {
      val todayStr = getTodayDateString()
      val yesterdayStr = getYesterdayDateString()
      val existingToday = dao.getDailyLoginForDate(todayStr)
      val existingYesterday = dao.getDailyLoginForDate(yesterdayStr)
      val currentStats = userStats.value

      if (existingToday == null) {
        val newStreak = if (existingYesterday != null || currentStats.lastLoginDate == yesterdayStr) {
          currentStats.streakDays + 1
        } else {
          // If first time or streak was broken
          if (currentStats.streakDays <= 0) 1 else 1
        }
        val bestStreak = maxOf(currentStats.bestStreakDays, newStreak)
        val updatedStats = currentStats.copy(
          streakDays = newStreak,
          bestStreakDays = bestStreak,
          lastLoginDate = todayStr,
          totalActiveDays = currentStats.totalActiveDays + 1,
          totalXp = currentStats.totalXp + 25
        )
        dao.insertDailyLogin(
          DailyLoginEntity(
            dateString = todayStr,
            practiceCompleted = false,
            xpEarned = 25,
            languagePracticedId = _selectedLanguage.value.id
          )
        )
        dao.saveUserStats(updatedStats)
      }
    }
  }

  fun markTodayPracticeCompleted() {
    viewModelScope.launch {
      val todayStr = getTodayDateString()
      val existingToday = dao.getDailyLoginForDate(todayStr)
      if (existingToday != null) {
        if (!existingToday.practiceCompleted) {
          dao.insertDailyLogin(existingToday.copy(practiceCompleted = true))
          awardXp(15)
        }
      } else {
        dao.insertDailyLogin(
          DailyLoginEntity(
            dateString = todayStr,
            practiceCompleted = true,
            xpEarned = 40,
            languagePracticedId = _selectedLanguage.value.id
          )
        )
        awardXp(15)
      }
    }
  }

  private fun calculateWeeklyStreak(logins: List<DailyLoginEntity>): List<DayStreakInfo> {
    val loginMap = logins.associateBy { it.dateString }
    val todayStr = getTodayDateString()
    val cal = Calendar.getInstance()
    cal.firstDayOfWeek = Calendar.MONDAY
    val currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    val daysFromMonday = if (currentDayOfWeek == Calendar.SUNDAY) 6 else currentDayOfWeek - Calendar.MONDAY
    cal.add(Calendar.DAY_OF_YEAR, -daysFromMonday)

    val dayAbbrs = listOf("M", "T", "W", "T", "F", "S", "S")
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val result = mutableListOf<DayStreakInfo>()

    for (i in 0 until 7) {
      val dateStr = format.format(cal.time)
      val dayNum = cal.get(Calendar.DAY_OF_MONTH)
      val isToday = (dateStr == todayStr)
      val loginRecord = loginMap[dateStr]
      val isLogged = loginRecord != null
      val isPracticed = loginRecord?.practiceCompleted == true

      result.add(
        DayStreakInfo(
          dayAbbr = dayAbbrs[i],
          dateNumber = dayNum,
          dateString = dateStr,
          isToday = isToday,
          isLogged = isLogged,
          isPracticed = isPracticed
        )
      )
      cal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return result
  }

  fun setDestination(dest: LaggetNavDestination) {
    _currentDestination.value = dest
    speechManager.stopSpeaking()
    speechManager.stopListening()
  }

  fun selectLanguage(language: Language) {
    _selectedLanguage.value = language
    val exercises = LanguageRepository.getSpeakingExercisesForLanguage(language.id)
    _selectedExercise.value = exercises.firstOrNull()
    _speakingEvaluation.value = null
    _spokenTranscript.value = ""
    speechManager.stopSpeaking()

    // If user has not taken diagnostic test for this language yet, trigger assessment
    val hasAssessed = allProficiencies.value.containsKey(language.id)
    if (!hasAssessed) {
      startDiagnosticTest(language)
    }
  }

  fun startDiagnosticTest(language: Language) {
    val questions = DiagnosticTestRepository.getDiagnosticQuestionsForLanguage(language)
    _diagnosticTestState.value = DiagnosticTestState(
      language = language,
      questions = questions,
      currentIndex = 0,
      selectedOptionIndex = null,
      isAnswerRevealed = false,
      userAnswers = emptyMap(),
      isCompleted = false,
      result = null
    )
  }

  fun retakeDiagnosticTest(language: Language) {
    startDiagnosticTest(language)
  }

  fun selectDiagnosticOption(index: Int) {
    val current = _diagnosticTestState.value ?: return
    if (current.isAnswerRevealed || current.isCompleted) return
    _diagnosticTestState.value = current.copy(selectedOptionIndex = index)
  }

  fun submitDiagnosticAnswer() {
    val current = _diagnosticTestState.value ?: return
    val selected = current.selectedOptionIndex ?: return
    val updatedAnswers = current.userAnswers + (current.currentIndex to selected)
    _diagnosticTestState.value = current.copy(
      isAnswerRevealed = true,
      userAnswers = updatedAnswers
    )
  }

  fun advanceToNextDiagnosticQuestion() {
    val current = _diagnosticTestState.value ?: return
    if (current.isLastQuestion) {
      finishAndSaveDiagnosticTest()
    } else {
      _diagnosticTestState.value = current.copy(
        currentIndex = current.currentIndex + 1,
        selectedOptionIndex = null,
        isAnswerRevealed = false
      )
    }
  }

  private fun finishAndSaveDiagnosticTest() {
    val current = _diagnosticTestState.value ?: return
    var correctCount = 0
    current.questions.forEachIndexed { index, q ->
      val chosen = current.userAnswers[index]
      if (chosen == q.correctOptionIndex) {
        correctCount++
      }
    }
    val total = current.questions.size
    val percentage = if (total > 0) (correctCount * 100) / total else 0
    val assessedLevel = ProficiencyLevel.fromScorePercentage(percentage)
    val feedback = when (assessedLevel) {
      ProficiencyLevel.BEGINNER -> "Great foundational start! You demonstrated initial familiarity with ${current.language.name} basics. We have tailored your learning path to master core greetings, script, and phonetics."
      ProficiencyLevel.INTERMEDIATE -> "Impressive proficiency! You have solid situational comprehension and vocabulary in ${current.language.name}. We have tailored your path to focus on conversational dialogues and idioms."
      ProficiencyLevel.ADVANCED -> "Outstanding mastery! You demonstrated nuanced understanding of advanced idioms, grammar, and cultural wisdom in ${current.language.name}. You are ready for eloquent dialogues and classical heritage!"
    }
    val result = DiagnosticTestResult(
      languageId = current.language.id,
      languageName = current.language.name,
      score = correctCount,
      totalQuestions = total,
      percentage = percentage,
      assessedLevel = assessedLevel,
      feedback = feedback,
      xpAwarded = 50
    )

    _diagnosticTestState.value = current.copy(
      isCompleted = true,
      result = result
    )

    // Save to Room & reward XP
    viewModelScope.launch {
      val today = getTodayDateString()
      val entity = LanguageProficiencyEntity(
        languageId = current.language.id,
        languageName = current.language.name,
        assessedLevel = assessedLevel.name,
        score = correctCount,
        totalQuestions = total,
        percentage = percentage,
        assessedDate = today
      )
      dao.saveProficiency(entity)
      awardXp(50)
      addWordsLearned(total)
      checkAndPersistUnlockedBadges()
    }
  }

  fun dismissDiagnosticTest() {
    _diagnosticTestState.value = null
  }

  fun resetLanguageProficiency(languageId: String) {
    viewModelScope.launch {
      dao.deleteProficiency(languageId)
    }
  }

  fun setRegion(region: WorldRegion) {
    _selectedRegion.value = region
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun selectCulturalCategory(category: CulturalCategory?) {
    _selectedCulturalCategory.value = category
  }

  fun toggleBookmark(item: CulturalInsightItem) {
    viewModelScope.launch {
      val isCurrently = bookmarkedIds.value.contains(item.id)
      if (isCurrently) {
        dao.deleteBookmark(item.id)
      } else {
        dao.insertBookmark(
          BookmarkEntity(
            insightId = item.id,
            languageId = item.languageId,
            categoryName = item.category.name,
            title = item.title
          )
        )
      }
    }
  }

  fun generateCustomCulturalInsight(topicOrCategory: String, customQuery: String) {
    viewModelScope.launch {
      _isGeneratingInsight.value = true
      _insightStatusMessage.value = "Consulting Gemini AI Cultural Model for ${_selectedLanguage.value.name}..."
      try {
        val newInsight = geminiService.generateDynamicCulturalInsight(
          languageName = _selectedLanguage.value.name,
          languageId = _selectedLanguage.value.id,
          topicOrCategory = topicOrCategory,
          userQuery = customQuery
        )
        _customAiInsights.value = listOf(newInsight) + _customAiInsights.value
        _insightStatusMessage.value = "New Cultural Module Generated!"
        awardXp(15)
      } catch (e: Exception) {
        _insightStatusMessage.value = "Failed to generate: ${e.message}"
      } finally {
        _isGeneratingInsight.value = false
      }
    }
  }

  fun selectExercise(exercise: SpeakingExercise) {
    _selectedExercise.value = exercise
    _speakingEvaluation.value = null
    _spokenTranscript.value = ""
    speechManager.stopSpeaking()
    speechManager.stopListening()
  }

  fun playAudioForExercise(exercise: SpeakingExercise) {
    speechManager.speak(exercise.phrase, exercise.audioLocaleTag)
  }

  fun playAudioText(text: String, localeTag: String) {
    speechManager.speak(text, localeTag)
  }

  fun startSpeakingPractice(exercise: SpeakingExercise) {
    _speakingEvaluation.value = null
    _spokenTranscript.value = ""
    speechManager.startListening(exercise.audioLocaleTag) { recognized ->
      _spokenTranscript.value = recognized
      evaluateSpokenSpeech(exercise, recognized)
    }
  }

  fun stopSpeakingPractice() {
    speechManager.stopListening()
  }

  fun evaluateManualTranscript(exercise: SpeakingExercise, transcript: String) {
    _spokenTranscript.value = transcript
    evaluateSpokenSpeech(exercise, transcript)
  }

  private fun evaluateSpokenSpeech(exercise: SpeakingExercise, spoken: String) {
    viewModelScope.launch {
      _isEvaluatingSpeech.value = true
      try {
        val eval = geminiService.evaluateSpeaking(
          languageName = _selectedLanguage.value.name,
          targetPhrase = exercise.phrase,
          spokenText = spoken
        )
        _speakingEvaluation.value = eval

        // Save to database
        dao.insertSpeakingAttempt(
          SpeakingHistoryEntity(
            languageId = exercise.languageId,
            exerciseId = exercise.id,
            targetPhrase = exercise.phrase,
            spokenText = spoken,
            score = eval.score,
            feedback = eval.pronunciationTip
          )
        )
        awardXp(eval.score / 4)
        markTodayPracticeCompleted()
      } catch (e: Exception) {
        // Fallback eval
      } finally {
        _isEvaluatingSpeech.value = false
      }
    }
  }

  fun selectBadgeCategory(category: AchievementCategory) {
    _selectedBadgeCategory.value = category
  }

  fun dismissBadgeCelebration() {
    _recentlyUnlockedBadge.value = null
  }

  fun checkAndPersistUnlockedBadges() {
    viewModelScope.launch {
      val today = getTodayDateString()
      val badges = allMilestoneBadges.value
      for (badge in badges) {
        if (badge.isUnlocked) {
          val already = dao.isAchievementUnlocked(badge.id)
          if (!already) {
            dao.insertAchievement(
              UnlockedAchievementEntity(
                badgeId = badge.id,
                title = badge.title,
                description = badge.description,
                category = badge.category.name,
                iconEmoji = badge.iconEmoji,
                unlockedDate = today
              )
            )
            _recentlyUnlockedBadge.value = badge
          }
        }
      }
    }
  }

  fun updateDailyGoalSettings(goalType: GoalType, targetValue: Int) {
    viewModelScope.launch {
      val current = userStats.value
      val safeTarget = targetValue.coerceIn(1, 100)
      val updated = current.copy(
        dailyGoalType = goalType.name,
        dailyGoalTarget = safeTarget
      )
      dao.saveUserStats(updated)
    }
  }

  fun incrementDailyGoalProgress(amount: Int = 1) {
    viewModelScope.launch {
      val current = userStats.value
      val today = getTodayDateString()
      val baseProgress = if (current.dailyGoalDate == today) current.dailyGoalProgress else 0
      val newProgress = baseProgress + amount
      val updated = current.copy(
        dailyGoalProgress = newProgress,
        dailyGoalDate = today,
        totalXp = current.totalXp + (amount * 5)
      )
      dao.saveUserStats(updated)
      if (newProgress >= current.dailyGoalTarget && baseProgress < current.dailyGoalTarget) {
        // Daily goal completed! Protect streak and award XP bonus
        markTodayPracticeCompleted()
      }
      checkAndPersistUnlockedBadges()
    }
  }

  fun addWordsLearned(count: Int) {
    viewModelScope.launch {
      val current = userStats.value
      val today = getTodayDateString()
      val baseGoalProgress = if (current.dailyGoalDate == today) current.dailyGoalProgress else 0
      val updatedGoalProgress = if (current.dailyGoalType == GoalType.WORDS.name) {
        baseGoalProgress + count
      } else {
        baseGoalProgress
      }
      val updated = current.copy(
        wordsLearnedCount = current.wordsLearnedCount + count,
        totalXp = current.totalXp + (count * 2),
        dailyGoalProgress = updatedGoalProgress,
        dailyGoalDate = today
      )
      dao.saveUserStats(updated)
      if (current.dailyGoalType == GoalType.WORDS.name && updatedGoalProgress >= current.dailyGoalTarget) {
        markTodayPracticeCompleted()
      }
      checkAndPersistUnlockedBadges()
    }
  }

  fun completeCulturalModule(categoryName: String = "Cultural Etiquette") {
    viewModelScope.launch {
      val current = userStats.value
      val today = getTodayDateString()
      val baseGoalProgress = if (current.dailyGoalDate == today) current.dailyGoalProgress else 0
      val updatedGoalProgress = if (current.dailyGoalType == GoalType.MINUTES.name) {
        baseGoalProgress + 5 // count completing a module as 5 minutes of study
      } else {
        baseGoalProgress + 4 // or 4 words learned
      }
      val updated = current.copy(
        culturalModulesCompletedCount = current.culturalModulesCompletedCount + 1,
        insightsExploredCount = current.insightsExploredCount + 4,
        wordsLearnedCount = current.wordsLearnedCount + 4,
        totalXp = current.totalXp + 60,
        dailyGoalProgress = updatedGoalProgress,
        dailyGoalDate = today
      )
      dao.saveUserStats(updated)
      if (updatedGoalProgress >= current.dailyGoalTarget) {
        markTodayPracticeCompleted()
      }
      checkAndPersistUnlockedBadges()
    }
  }

  private fun awardXp(amount: Int) {
    viewModelScope.launch {
      val current = userStats.value
      val today = getTodayDateString()
      val baseGoalProgress = if (current.dailyGoalDate == today) current.dailyGoalProgress else 0
      val updatedGoalProgress = if (current.dailyGoalType == GoalType.MINUTES.name) {
        baseGoalProgress + 2 // 2 minutes practice
      } else {
        baseGoalProgress + 2 // 2 words practiced
      }
      val updated = current.copy(
        totalXp = current.totalXp + amount,
        speakingSessionsCount = current.speakingSessionsCount + 1,
        wordsLearnedCount = current.wordsLearnedCount + 3,
        dailyGoalProgress = updatedGoalProgress,
        dailyGoalDate = today
      )
      dao.saveUserStats(updated)
      if (updatedGoalProgress >= current.dailyGoalTarget) {
        markTodayPracticeCompleted()
      }
      checkAndPersistUnlockedBadges()
    }
  }

  override fun onCleared() {
    super.onCleared()
    speechManager.release()
  }
}
