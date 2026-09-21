package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.LanguageProficiencyEntity
import com.example.data.local.UserLearningStats
import com.example.data.model.DailyLearningGoal
import com.example.data.model.GoalType
import com.example.data.model.Language
import com.example.data.model.ProficiencyLevel
import com.example.data.model.WorldRegion
import com.example.ui.components.AdjustGoalDialog
import com.example.ui.components.DailyLearningGoalCard
import com.example.ui.components.DailyStreakCard
import com.example.ui.components.DayStreakInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Refresh
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SuccessGreen

@Composable
fun LanguagesScreen(
  languages: List<Language>,
  selectedLanguage: Language,
  selectedRegion: WorldRegion,
  searchQuery: String,
  userStats: UserLearningStats,
  weeklyStreakList: List<DayStreakInfo>,
  isTodayPracticed: Boolean,
  dailyLearningGoal: DailyLearningGoal,
  proficiencies: Map<String, LanguageProficiencyEntity>,
  onLanguageSelected: (Language) -> Unit,
  onRegionSelected: (WorldRegion) -> Unit,
  onSearchQueryChanged: (String) -> Unit,
  onPlayGreeting: (Language) -> Unit,
  onOpenCultureForLanguage: (Language) -> Unit,
  onPracticeStreakClick: () -> Unit,
  onUpdateGoal: (GoalType, Int) -> Unit,
  onQuickGoalIncrement: () -> Unit,
  onTakeDiagnosticTest: (Language) -> Unit,
  modifier: Modifier = Modifier
) {
  var showAdjustGoalDialog by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("languages_screen")
  ) {
    Spacer(modifier = Modifier.height(12.dp))

    // Search bar
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchQueryChanged,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("language_search_input"),
      placeholder = {
        Text("Search by language, script, or region…", style = MaterialTheme.typography.bodyMedium)
      },
      leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = RitNavyPrimary)
      },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { onSearchQueryChanged("") }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear search")
          }
        }
      },
      shape = RoundedCornerShape(14.dp),
      singleLine = true,
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = RitCobalt,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
      )
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Continent / Region Filter Chips
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      WorldRegion.values().forEach { region ->
        val isSelected = selectedRegion == region
        FilterChip(
          selected = isSelected,
          onClick = { onRegionSelected(region) },
          label = {
            Text(
              text = region.displayName,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            )
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = RitNavyPrimary,
            selectedLabelColor = Color.White
          ),
          border = FilterChipDefaults.filterChipBorder(
            borderColor = if (isSelected) RitNavyPrimary else MaterialTheme.colorScheme.outline,
            selectedBorderColor = RitNavyPrimary,
            enabled = true,
            selected = isSelected
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Summary banner
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${languages.size} World Languages Cataloged",
        style = MaterialTheme.typography.labelMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.SemiBold
        )
      )
      Text(
        text = "Ramco Institute Autonomous Curriculum",
        style = MaterialTheme.typography.labelSmall.copy(
          color = RitTeal,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Language List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      item {
        DailyLearningGoalCard(
          goal = dailyLearningGoal,
          onAdjustGoalClick = { showAdjustGoalDialog = true },
          onQuickIncrement = onQuickGoalIncrement
        )
      }

      item {
        DailyStreakCard(
          userStats = userStats,
          weeklyStreakList = weeklyStreakList,
          todayPracticed = isTodayPracticed,
          onPracticeClick = onPracticeStreakClick
        )
      }

      items(languages, key = { it.id }) { language ->
        val isCurrent = language.id == selectedLanguage.id
        val proficiency = proficiencies[language.id]
        LanguageItemCard(
          language = language,
          isSelected = isCurrent,
          proficiency = proficiency,
          onSelect = { onLanguageSelected(language) },
          onPlayGreeting = { onPlayGreeting(language) },
          onOpenCulture = { onOpenCultureForLanguage(language) },
          onTakeDiagnosticTest = { onTakeDiagnosticTest(language) }
        )
      }
    }

    if (showAdjustGoalDialog) {
      AdjustGoalDialog(
        currentGoal = dailyLearningGoal,
        onSaveGoal = { type, target -> onUpdateGoal(type, target) },
        onDismiss = { showAdjustGoalDialog = false }
      )
    }
  }
}

@Composable
fun LanguageItemCard(
  language: Language,
  isSelected: Boolean,
  proficiency: LanguageProficiencyEntity?,
  onSelect: () -> Unit,
  onPlayGreeting: () -> Unit,
  onOpenCulture: () -> Unit,
  onTakeDiagnosticTest: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .testTag("language_card_${language.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) RitCobalt.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(
      width = if (isSelected) 2.dp else 1.dp,
      color = if (isSelected) RitNavyPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(if (isSelected) RitGoldAccent.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
          ) {
            Text(text = language.flagEmoji, fontSize = 24.sp)
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = language.name,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = language.nativeName,
                style = MaterialTheme.typography.titleSmall.copy(
                  color = RitCobalt,
                  fontWeight = FontWeight.SemiBold
                )
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "${language.scriptName} • ${language.region}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 11.sp
                )
              )
            }
          }
        }

        Column(
          horizontalAlignment = Alignment.End,
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          if (isSelected) {
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = RitNavyPrimary,
              contentColor = Color.White
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Active Language",
                  tint = RitGoldAccent,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Active",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
              }
            }
          }

          // Proficiency Level Badge
          if (proficiency != null) {
            val level = try {
              ProficiencyLevel.valueOf(proficiency.assessedLevel)
            } catch (e: Exception) {
              ProficiencyLevel.BEGINNER
            }
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = when (level) {
                ProficiencyLevel.BEGINNER -> RitTeal.copy(alpha = 0.15f)
                ProficiencyLevel.INTERMEDIATE -> RitCobalt.copy(alpha = 0.15f)
                ProficiencyLevel.ADVANCED -> RitGoldAccent.copy(alpha = 0.2f)
              },
              modifier = Modifier.testTag("proficiency_badge_${language.id}")
            ) {
              Text(
                text = "${level.iconEmoji} ${level.title} (${proficiency.percentage}%)",
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = when (level) {
                    ProficiencyLevel.BEGINNER -> RitTeal
                    ProficiencyLevel.INTERMEDIATE -> RitCobalt
                    ProficiencyLevel.ADVANCED -> RitNavyPrimary
                  },
                  fontSize = 10.sp
                )
              )
            }
          } else {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = RitGoldAccent.copy(alpha = 0.18f),
              modifier = Modifier.testTag("diagnostic_pending_tag_${language.id}")
            ) {
              Text(
                text = "📝 Diagnostic Ready",
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyDark,
                  fontSize = 10.sp
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Greeting snippet
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = language.formalGreeting,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Text(
              text = language.greetingTranslation,
              style = MaterialTheme.typography.labelSmall.copy(
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }

          IconButton(
            onClick = onPlayGreeting,
            modifier = Modifier
              .size(36.dp)
              .testTag("play_greeting_${language.id}")
          ) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Listen Greeting",
              tint = RitNavyPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Proverb & speakers
      Text(
        text = "“${language.mottoOrProverb}”",
        style = MaterialTheme.typography.bodySmall.copy(
          fontStyle = FontStyle.Italic,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 12.sp
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = RitTeal.copy(alpha = 0.12f)
        ) {
          Text(
            text = "👥 ~${language.approximateSpeakers} speakers",
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
              color = RitTeal,
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp
            )
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          if (proficiency == null) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = RitNavyPrimary,
              modifier = Modifier
                .clickable { onTakeDiagnosticTest() }
                .testTag("take_diagnostic_test_${language.id}")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Assignment,
                  contentDescription = "Take Diagnostic Test",
                  tint = RitGoldAccent,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Assess Level",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
            }
          } else {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier
                .clickable { onTakeDiagnosticTest() }
                .testTag("retake_diagnostic_test_${language.id}")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Refresh,
                  contentDescription = "Retake Assessment",
                  tint = RitCobalt,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Retake",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = RitCobalt,
                    fontWeight = FontWeight.SemiBold
                  )
                )
              }
            }
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = RitCobalt.copy(alpha = 0.15f),
            modifier = Modifier.clickable { onOpenCulture() }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Explore,
                contentDescription = "Explore Culture",
                tint = RitNavyPrimary,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Culture",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = RitNavyPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }
        }
      }
    }
  }
}
