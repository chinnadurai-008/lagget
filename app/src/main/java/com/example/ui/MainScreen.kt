package com.example.ui

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ui.components.DiagnosticProficiencyDialog
import com.example.ui.components.LaggetTopBar
import com.example.ui.screens.CulturalInsightsScreen
import com.example.ui.screens.LanguagesScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RitHeritageScreen
import com.example.ui.screens.SpeakingPracticeScreen
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary

@Composable
fun MainScreen(
  viewModel: LaggetViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  val currentDestination by viewModel.currentDestination.collectAsState()
  val selectedLanguage by viewModel.selectedLanguage.collectAsState()
  val selectedRegion by viewModel.selectedRegion.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val filteredLanguages by viewModel.filteredLanguages.collectAsState()

  val selectedCulturalCategory by viewModel.selectedCulturalCategory.collectAsState()
  val activeCulturalInsights by viewModel.activeCulturalInsights.collectAsState()
  val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()
  val isGeneratingInsight by viewModel.isGeneratingInsight.collectAsState()
  val insightStatusMessage by viewModel.insightStatusMessage.collectAsState()

  val selectedExercise by viewModel.selectedExercise.collectAsState()
  val isListening by viewModel.speechManager.isListening.collectAsState()
  val audioRms by viewModel.speechManager.audioRmsLevel.collectAsState()
  val spokenTranscript by viewModel.spokenTranscript.collectAsState()
  val isEvaluatingSpeech by viewModel.isEvaluatingSpeech.collectAsState()
  val speakingEvaluation by viewModel.speakingEvaluation.collectAsState()
  val speakingHistory by viewModel.recentSpeakingHistory.collectAsState()
  val userStats by viewModel.userStats.collectAsState()
  val weeklyStreakList by viewModel.weeklyStreakList.collectAsState()
  val isTodayPracticed by viewModel.isTodayPracticed.collectAsState()
  val dailyLearningGoal by viewModel.dailyLearningGoal.collectAsState()
  val badges by viewModel.filteredBadges.collectAsState()
  val selectedBadgeCategory by viewModel.selectedBadgeCategory.collectAsState()
  val recentlyUnlockedBadge by viewModel.recentlyUnlockedBadge.collectAsState()
  val proficiencies by viewModel.allProficiencies.collectAsState()
  val diagnosticTestState by viewModel.diagnosticTestState.collectAsState()

  // Audio permission launcher
  val audioPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      selectedExercise?.let { viewModel.startSpeakingPractice(it) }
    } else {
      Toast.makeText(
        context,
        "Microphone permission is required for AI speaking practice. You can also type text to test.",
        Toast.LENGTH_LONG
      ).show()
    }
  }

  Scaffold(
    modifier = modifier.fillMaxSize().testTag("main_screen_scaffold"),
    topBar = {
      LaggetTopBar(
        selectedLanguage = selectedLanguage,
        userStats = userStats,
        onStreakClick = { viewModel.setDestination(LaggetNavDestination.PROFILE) }
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = RitNavyDark,
        contentColor = Color.White,
        modifier = Modifier.testTag("lagget_bottom_nav")
      ) {
        LaggetNavDestination.values().forEach { destination ->
          val isSelected = currentDestination == destination
          NavigationBarItem(
            selected = isSelected,
            onClick = { viewModel.setDestination(destination) },
            icon = {
              Text(
                text = destination.icon,
                fontSize = if (isSelected) 22.sp else 18.sp
              )
            },
            label = {
              Text(
                text = destination.label,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = RitGoldAccent,
              selectedTextColor = RitGoldAccent,
              unselectedIconColor = Color.White.copy(alpha = 0.6f),
              unselectedTextColor = Color.White.copy(alpha = 0.6f),
              indicatorColor = RitNavyPrimary
            ),
            modifier = Modifier.testTag("nav_item_${destination.name.lowercase()}")
          )
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (currentDestination) {
        LaggetNavDestination.LANGUAGES -> {
          LanguagesScreen(
            languages = filteredLanguages,
            selectedLanguage = selectedLanguage,
            selectedRegion = selectedRegion,
            searchQuery = searchQuery,
            userStats = userStats,
            weeklyStreakList = weeklyStreakList,
            isTodayPracticed = isTodayPracticed,
            dailyLearningGoal = dailyLearningGoal,
            proficiencies = proficiencies,
            onLanguageSelected = { lang ->
              viewModel.selectLanguage(lang)
            },
            onRegionSelected = { reg ->
              viewModel.setRegion(reg)
            },
            onSearchQueryChanged = { q ->
              viewModel.setSearchQuery(q)
            },
            onPlayGreeting = { lang ->
              viewModel.playAudioText(lang.formalGreeting, lang.ttsLocaleCode)
            },
            onOpenCultureForLanguage = { lang ->
              viewModel.selectLanguage(lang)
              viewModel.setDestination(LaggetNavDestination.CULTURE)
            },
            onPracticeStreakClick = {
              viewModel.setDestination(LaggetNavDestination.SPEAKING)
            },
            onUpdateGoal = { type, target ->
              viewModel.updateDailyGoalSettings(type, target)
            },
            onQuickGoalIncrement = {
              viewModel.incrementDailyGoalProgress(1)
            },
            onTakeDiagnosticTest = { lang ->
              viewModel.startDiagnosticTest(lang)
            }
          )
        }

        LaggetNavDestination.CULTURE -> {
          CulturalInsightsScreen(
            selectedLanguage = selectedLanguage,
            insights = activeCulturalInsights,
            selectedCategory = selectedCulturalCategory,
            bookmarkedIds = bookmarkedIds,
            isGeneratingInsight = isGeneratingInsight,
            statusMessage = insightStatusMessage,
            onCategorySelected = { cat ->
              viewModel.selectCulturalCategory(cat)
            },
            onToggleBookmark = { item ->
              viewModel.toggleBookmark(item)
            },
            onPlayAudio = { text, locale ->
              viewModel.playAudioText(text, locale)
            },
            onGenerateCustomInsight = { topic, query ->
              viewModel.generateCustomCulturalInsight(topic, query)
            },
            onGoToSpeakingPractice = {
              viewModel.setDestination(LaggetNavDestination.SPEAKING)
            }
          )
        }

        LaggetNavDestination.SPEAKING -> {
          SpeakingPracticeScreen(
            selectedLanguage = selectedLanguage,
            selectedExercise = selectedExercise,
            isListening = isListening,
            spokenTranscript = spokenTranscript,
            isEvaluatingSpeech = isEvaluatingSpeech,
            evaluation = speakingEvaluation,
            historyList = speakingHistory,
            audioRmsLevel = audioRms,
            onSelectExercise = { ex ->
              viewModel.selectExercise(ex)
            },
            onPlayAudio = { ex ->
              viewModel.playAudioForExercise(ex)
            },
            onStartListening = { ex ->
              val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
              ) == PackageManager.PERMISSION_GRANTED

              if (hasPermission) {
                viewModel.startSpeakingPractice(ex)
              } else {
                audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
              }
            },
            onStopListening = {
              viewModel.stopSpeakingPractice()
            },
            onManualEvaluate = { ex, transcript ->
              viewModel.evaluateManualTranscript(ex, transcript)
            }
          )
        }

        LaggetNavDestination.PROFILE -> {
          ProfileScreen(
            userStats = userStats,
            badges = badges,
            selectedCategory = selectedBadgeCategory,
            recentlyUnlockedBadge = recentlyUnlockedBadge,
            dailyLearningGoal = dailyLearningGoal,
            proficiencies = proficiencies,
            onSelectCategory = { cat -> viewModel.selectBadgeCategory(cat) },
            onDismissCelebration = { viewModel.dismissBadgeCelebration() },
            onNavigateToSpeaking = { viewModel.setDestination(LaggetNavDestination.SPEAKING) },
            onNavigateToCulture = { viewModel.setDestination(LaggetNavDestination.CULTURE) },
            onAddWordsLearned = { count -> viewModel.addWordsLearned(count) },
            onCompleteCulturalModule = { viewModel.completeCulturalModule() },
            onUpdateGoal = { type, target -> viewModel.updateDailyGoalSettings(type, target) },
            onQuickGoalIncrement = { viewModel.incrementDailyGoalProgress(1) }
          )
        }

        LaggetNavDestination.HERITAGE -> {
          RitHeritageScreen()
        }
      }
    }

    // Diagnostic Proficiency Test Overlay Dialog
    diagnosticTestState?.let { testState ->
      DiagnosticProficiencyDialog(
        testState = testState,
        onSelectOption = { optIndex -> viewModel.selectDiagnosticOption(optIndex) },
        onSubmitAnswer = { viewModel.submitDiagnosticAnswer() },
        onNextQuestion = { viewModel.advanceToNextDiagnosticQuestion() },
        onRetakeTest = { viewModel.retakeDiagnosticTest(testState.language) },
        onDismiss = { viewModel.dismissDiagnosticTest() }
      )
    }
  }
}
