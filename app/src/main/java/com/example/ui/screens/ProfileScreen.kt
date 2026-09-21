package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.LanguageProficiencyEntity
import com.example.data.local.UserLearningStats
import com.example.data.model.AchievementBadge
import com.example.data.model.AchievementCategory
import com.example.data.model.ProficiencyLevel
import com.example.ui.components.AchievementBadgeCard
import com.example.ui.components.BadgeCelebrationBanner
import com.example.ui.components.BadgeDetailDialog
import com.example.data.model.DailyLearningGoal
import com.example.data.model.GoalType
import com.example.ui.components.AdjustGoalDialog
import com.example.ui.components.DailyLearningGoalCard
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@Composable
fun ProfileScreen(
  userStats: UserLearningStats,
  badges: List<AchievementBadge>,
  selectedCategory: AchievementCategory,
  recentlyUnlockedBadge: AchievementBadge?,
  dailyLearningGoal: DailyLearningGoal,
  proficiencies: Map<String, LanguageProficiencyEntity> = emptyMap(),
  onSelectCategory: (AchievementCategory) -> Unit,
  onDismissCelebration: () -> Unit,
  onNavigateToSpeaking: () -> Unit,
  onNavigateToCulture: () -> Unit,
  onAddWordsLearned: (Int) -> Unit,
  onCompleteCulturalModule: () -> Unit,
  onUpdateGoal: (GoalType, Int) -> Unit,
  onQuickGoalIncrement: () -> Unit,
  modifier: Modifier = Modifier
) {
  var inspectingBadge by remember { mutableStateOf<AchievementBadge?>(null) }
  var showAdjustGoalDialog by remember { mutableStateOf(false) }
  val unlockedCount = badges.count { it.isUnlocked }
  val currentLevel = (userStats.totalXp / 100) + 1
  val currentLevelXp = userStats.totalXp % 100
  val levelProgress = (currentLevelXp / 100f).coerceIn(0f, 1f)

  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag("profile_screen_list"),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 88.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // 1. Celebration Banner if any badge just unlocked
      if (recentlyUnlockedBadge != null) {
        item {
          BadgeCelebrationBanner(
            badge = recentlyUnlockedBadge,
            onDismiss = onDismissCelebration
          )
        }
      }

      // 2. Learner Profile Header
      item {
        LearnerProfileHeaderCard(
          userStats = userStats,
          currentLevel = currentLevel,
          levelProgress = levelProgress,
          currentLevelXp = currentLevelXp,
          unlockedCount = unlockedCount,
          totalBadges = badges.size
        )
      }

      // 3. Daily Learning Goal Card with Progress & Adjustments
      item {
        DailyLearningGoalCard(
          goal = dailyLearningGoal,
          onAdjustGoalClick = { showAdjustGoalDialog = true },
          onQuickIncrement = onQuickGoalIncrement
        )
      }

      // 4. Assessed Language Proficiencies
      if (proficiencies.isNotEmpty()) {
        item {
          Card(
            modifier = Modifier.fillMaxWidth().testTag("profile_proficiencies_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, RitCobalt.copy(alpha = 0.2f))
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Assessed Language Proficiencies",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = RitNavyDark
                  )
                )
                Text(
                  text = "${proficiencies.size} Tested",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = RitCobalt,
                    fontWeight = FontWeight.Bold
                  )
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              proficiencies.values.forEach { prof ->
                val level = try {
                  ProficiencyLevel.valueOf(prof.assessedLevel)
                } catch (e: Exception) {
                  ProficiencyLevel.BEGINNER
                }

                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                ) {
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column {
                      Text(
                        text = prof.languageName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                      )
                      Text(
                        text = "Tested ${prof.assessedDate} • ${prof.percentage}% Accuracy",
                        style = MaterialTheme.typography.labelSmall.copy(
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                          fontSize = 11.sp
                        )
                      )
                    }

                    Surface(
                      shape = RoundedCornerShape(6.dp),
                      color = when (level) {
                        ProficiencyLevel.BEGINNER -> RitTeal.copy(alpha = 0.15f)
                        ProficiencyLevel.INTERMEDIATE -> RitCobalt.copy(alpha = 0.15f)
                        ProficiencyLevel.ADVANCED -> RitGoldAccent.copy(alpha = 0.2f)
                      }
                    ) {
                      Text(
                        text = "${level.iconEmoji} ${level.title}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                          fontWeight = FontWeight.Bold,
                          color = when (level) {
                            ProficiencyLevel.BEGINNER -> RitTeal
                            ProficiencyLevel.INTERMEDIATE -> RitCobalt
                            ProficiencyLevel.ADVANCED -> RitNavyPrimary
                          }
                        )
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }

      // 5. Quick Learning Milestone Accelerators
      item {
        MilestoneActionsCard(
          onNavigateToSpeaking = onNavigateToSpeaking,
          onNavigateToCulture = onNavigateToCulture,
          onAddWordsLearned = { onAddWordsLearned(5) },
          onCompleteCulturalModule = onCompleteCulturalModule
        )
      }

      // 4. Badges Section Header & Category Filter Row
      item {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = RitGoldAccent,
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Milestones & Badges",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyDark
                ),
                modifier = Modifier.testTag("badges_section_title")
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = RitNavyPrimary.copy(alpha = 0.1f),
              border = BorderStroke(1.dp, RitNavyPrimary.copy(alpha = 0.2f))
            ) {
              Text(
                text = "$unlockedCount / ${badges.size} Unlocked",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyPrimary
                ),
                modifier = Modifier
                  .padding(horizontal = 8.dp, vertical = 4.dp)
                  .testTag("unlocked_badges_ratio")
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Filter chips
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().testTag("badge_category_filters")
          ) {
            items(AchievementCategory.values()) { category ->
              val isSelected = category == selectedCategory
              FilterChip(
                selected = isSelected,
                onClick = { onSelectCategory(category) },
                label = {
                  Text(
                    text = "${category.iconEmoji} ${category.displayName}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = RitNavyPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }
      }

      // 5. Badge Cards List
      items(badges, key = { it.id }) { badge ->
        AchievementBadgeCard(
          badge = badge,
          onClick = { inspectingBadge = badge }
        )
      }

      // 6. RIT Institutional Honor Pledge
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = RitNavyDark),
          border = BorderStroke(1.dp, RitGoldAccent.copy(alpha = 0.4f))
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              tint = RitGoldAccent,
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Ramco Institute of Technology (Autonomous)",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = RitGoldAccent,
                  fontWeight = FontWeight.Bold
                )
              )
              Text(
                text = "Global Multilingual Excellence & Cultural Heritage",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = Color.White.copy(alpha = 0.85f),
                  fontSize = 11.sp
                )
              )
            }
          }
        }
      }
    }

    // Badge Inspect Dialog
    inspectingBadge?.let { badge ->
      BadgeDetailDialog(
        badge = badge,
        onDismiss = { inspectingBadge = null }
      )
    }

    // Adjust Daily Learning Goal Dialog
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
private fun LearnerProfileHeaderCard(
  userStats: UserLearningStats,
  currentLevel: Int,
  levelProgress: Float,
  currentLevelXp: Int,
  unlockedCount: Int,
  totalBadges: Int
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("learner_profile_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.5.dp, RitNavyPrimary.copy(alpha = 0.2f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Profile top row
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Avatar circle
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(RitGoldAccent, RitNavyPrimary)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Text(text = "🎓", fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = userStats.learnerName,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyDark
              )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = RitGoldAccent.copy(alpha = 0.25f)
            ) {
              Text(
                text = "Lvl $currentLevel",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp,
                  color = RitNavyDark
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Text(
            text = userStats.department,
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )

          Text(
            text = "Ramco Institute of Technology (Autonomous)",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = RitCobalt
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Level Progress Bar
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Level $currentLevel Scholar Progress",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              color = RitNavyDark
            )
          )
          Text(
            text = "$currentLevelXp / 100 XP to Lvl ${currentLevel + 1}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
          progress = { levelProgress },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
          color = RitGoldAccent,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 4 Key Stats Metrics
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        ProfileStatMetric(
          icon = "⚡",
          value = "${userStats.totalXp}",
          label = "Total XP"
        )
        ProfileStatMetric(
          icon = "🔥",
          value = "${userStats.streakDays}d",
          label = "Streak"
        )
        ProfileStatMetric(
          icon = "📚",
          value = "${userStats.wordsLearnedCount}",
          label = "Words Learned"
        )
        ProfileStatMetric(
          icon = "🏆",
          value = "$unlockedCount/$totalBadges",
          label = "Badges"
        )
      }
    }
  }
}

@Composable
private fun ProfileStatMetric(
  icon: String,
  value: String,
  label: String
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
    modifier = Modifier.width(74.dp)
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = icon, fontSize = 16.sp)
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = RitNavyDark
        )
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 9.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        maxLines = 1
      )
    }
  }
}

@Composable
private fun MilestoneActionsCard(
  onNavigateToSpeaking: () -> Unit,
  onNavigateToCulture: () -> Unit,
  onAddWordsLearned: () -> Unit,
  onCompleteCulturalModule: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = RitCobalt.copy(alpha = 0.08f)),
    border = BorderStroke(1.dp, RitCobalt.copy(alpha = 0.25f))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = null,
          tint = RitCobalt,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Accelerate Badge Milestones",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = RitNavyDark
          )
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onNavigateToSpeaking,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = RitNavyPrimary,
            contentColor = Color.White
          ),
          modifier = Modifier.weight(1f).testTag("action_go_speaking_btn")
        ) {
          Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Speaking", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        }

        Button(
          onClick = onNavigateToCulture,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = RitCobalt,
            contentColor = Color.White
          ),
          modifier = Modifier.weight(1f).testTag("action_go_culture_btn")
        ) {
          Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Culture", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onAddWordsLearned,
          shape = RoundedCornerShape(10.dp),
          border = BorderStroke(1.dp, RitNavyPrimary),
          modifier = Modifier.weight(1f).testTag("action_learn_words_btn")
        ) {
          Text("+5 Words Practice", style = MaterialTheme.typography.labelSmall.copy(color = RitNavyPrimary, fontWeight = FontWeight.Bold))
        }

        OutlinedButton(
          onClick = onCompleteCulturalModule,
          shape = RoundedCornerShape(10.dp),
          border = BorderStroke(1.dp, RitTeal),
          modifier = Modifier.weight(1f).testTag("action_complete_module_btn")
        ) {
          Text("+1 Module Done", style = MaterialTheme.typography.labelSmall.copy(color = RitTeal, fontWeight = FontWeight.Bold))
        }
      }
    }
  }
}
