package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.data.model.DailyLearningGoal
import com.example.data.model.GoalType
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SuccessGreen

@Composable
fun DailyLearningGoalCard(
  goal: DailyLearningGoal,
  onAdjustGoalClick: () -> Unit,
  onQuickIncrement: () -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = goal.progressFraction,
    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
    label = "goal_progress"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("daily_learning_goal_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(
      width = 1.5.dp,
      color = if (goal.isCompleted) SuccessGreen.copy(alpha = 0.7f) else RitCobalt.copy(alpha = 0.35f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header: Goal Title, Goal Type Badge, Edit/Adjust Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(
                if (goal.isCompleted) {
                  Brush.radialGradient(listOf(SuccessGreen.copy(alpha = 0.25f), Color.Transparent))
                } else {
                  Brush.radialGradient(listOf(RitCobalt.copy(alpha = 0.2f), Color.Transparent))
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (goal.isCompleted) "🎯" else goal.goalType.iconEmoji,
              fontSize = 22.sp
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Daily Learning Goal",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyDark
                )
              )
              if (goal.isCompleted) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Goal Achieved",
                  tint = SuccessGreen,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
            Text(
              text = "Target: ${goal.targetValue} ${goal.goalType.unit} per day",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        // Adjust Goal Button
        OutlinedButton(
          onClick = onAdjustGoalClick,
          shape = RoundedCornerShape(10.dp),
          border = BorderStroke(1.dp, RitNavyPrimary.copy(alpha = 0.5f)),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier.testTag("adjust_goal_btn")
        ) {
          Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Adjust Goal",
            tint = RitNavyPrimary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Adjust",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = RitNavyPrimary
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Progress Metrics Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
      ) {
        Row(verticalAlignment = Alignment.Bottom) {
          Text(
            text = "${goal.currentProgress}",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = if (goal.isCompleted) SuccessGreen else RitNavyDark
            ),
            modifier = Modifier.testTag("current_goal_progress_text")
          )
          Text(
            text = " / ${goal.targetValue} ${goal.goalType.unit}",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (goal.isCompleted) SuccessGreen.copy(alpha = 0.15f) else RitCobalt.copy(alpha = 0.12f)
        ) {
          Text(
            text = if (goal.isCompleted) "Completed 🎉" else "${goal.percentageText} Done",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = if (goal.isCompleted) SuccessGreen else RitNavyPrimary,
              fontSize = 11.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Progress Bar
      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(10.dp)
          .clip(RoundedCornerShape(5.dp))
          .testTag("daily_goal_progress_bar"),
        color = if (goal.isCompleted) SuccessGreen else RitCobalt,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Action Footer: motivational reminder + quick progress button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (goal.isCompleted) {
            "Great job! Consistency drives multilingual fluency."
          } else {
            val remaining = (goal.targetValue - goal.currentProgress).coerceAtLeast(0)
            "$remaining ${goal.goalType.unit} left to reach today's target"
          },
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          ),
          modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = RitNavyPrimary.copy(alpha = 0.08f),
          border = BorderStroke(1.dp, RitNavyPrimary.copy(alpha = 0.2f)),
          modifier = Modifier.clickable { onQuickIncrement() }.testTag("quick_progress_chip")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Quick add",
              tint = RitNavyPrimary,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = if (goal.goalType == GoalType.WORDS) "+1 Word" else "+2 Mins",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary,
                fontSize = 11.sp
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun AdjustGoalDialog(
  currentGoal: DailyLearningGoal,
  onSaveGoal: (GoalType, Int) -> Unit,
  onDismiss: () -> Unit
) {
  var selectedType by remember { mutableStateOf(currentGoal.goalType) }
  var targetValue by remember { mutableFloatStateOf(currentGoal.targetValue.toFloat()) }

  val minVal = if (selectedType == GoalType.WORDS) 5f else 5f
  val maxVal = if (selectedType == GoalType.WORDS) 50f else 60f
  val step = if (selectedType == GoalType.WORDS) 5 else 5

  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      Button(
        onClick = {
          onSaveGoal(selectedType, targetValue.toInt())
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = RitNavyPrimary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.testTag("save_goal_settings_btn")
      ) {
        Text("Save Goal", fontWeight = FontWeight.Bold, color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.TrackChanges,
          contentDescription = null,
          tint = RitCobalt,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Set Daily Learning Goal",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = RitNavyDark
          )
        )
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "Choose your goal metric and daily target to boost learning momentum:",
          style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Goal Type Selector (Words per Day vs Minutes per Day)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          GoalType.values().forEach { type ->
            val isSelected = selectedType == type
            FilterChip(
              selected = isSelected,
              onClick = {
                selectedType = type
                targetValue = if (type == GoalType.WORDS) 10f else 15f
              },
              label = {
                Text(
                  text = "${type.iconEmoji} ${type.label}",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                  )
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = RitNavyPrimary,
                selectedLabelColor = Color.White
              ),
              modifier = Modifier.weight(1f).testTag("goal_type_chip_${type.name}")
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Target Value Display & Increments
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Daily Target",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              IconButton(
                onClick = {
                  val updated = (targetValue - step).coerceAtLeast(minVal)
                  targetValue = updated
                },
                modifier = Modifier.testTag("decrement_goal_btn")
              ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
              }

              Text(
                text = "${targetValue.toInt()} ${selectedType.unit}",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = RitNavyPrimary
                ),
                modifier = Modifier.padding(horizontal = 14.dp).testTag("dialog_target_value_text")
              )

              IconButton(
                onClick = {
                  val updated = (targetValue + step).coerceAtMost(maxVal)
                  targetValue = updated
                },
                modifier = Modifier.testTag("increment_goal_btn")
              ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Slider for fine tuning
            Slider(
              value = targetValue,
              onValueChange = { targetValue = it },
              valueRange = minVal..maxVal,
              steps = ((maxVal - minVal) / step).toInt() - 1,
              colors = SliderDefaults.colors(
                thumbColor = RitNavyPrimary,
                activeTrackColor = RitCobalt
              ),
              modifier = Modifier.fillMaxWidth().testTag("goal_slider")
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Preset Shortcut Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          val presets = if (selectedType == GoalType.WORDS) listOf(5, 10, 15, 25) else listOf(10, 15, 20, 30)
          presets.forEach { preset ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (targetValue.toInt() == preset) RitGoldAccent.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant,
              border = BorderStroke(
                1.dp,
                if (targetValue.toInt() == preset) RitGoldAccent else Color.Transparent
              ),
              modifier = Modifier
                .clickable { targetValue = preset.toFloat() }
                .padding(horizontal = 2.dp)
            ) {
              Text(
                text = "$preset ${selectedType.unit}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp,
                  color = RitNavyDark
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }
  )
}
