package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.DiagnosticQuestion
import com.example.data.model.DiagnosticTestResult
import com.example.data.model.DiagnosticTestState
import com.example.data.model.ProficiencyLevel
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SoftRed
import com.example.ui.theme.SuccessGreen

@Composable
fun DiagnosticProficiencyDialog(
  testState: DiagnosticTestState,
  onSelectOption: (Int) -> Unit,
  onSubmitAnswer: () -> Unit,
  onNextQuestion: () -> Unit,
  onRetakeTest: () -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxHeight(0.92f)
        .testTag("diagnostic_proficiency_dialog"),
      shape = RoundedCornerShape(24.dp),
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 6.dp,
      shadowElevation = 12.dp
    ) {
      if (testState.isCompleted && testState.result != null) {
        DiagnosticResultView(
          result = testState.result,
          onRetake = onRetakeTest,
          onConfirm = onDismiss
        )
      } else {
        DiagnosticQuizView(
          testState = testState,
          onSelectOption = onSelectOption,
          onSubmitAnswer = onSubmitAnswer,
          onNextQuestion = onNextQuestion,
          onDismiss = onDismiss
        )
      }
    }
  }
}

@Composable
private fun DiagnosticQuizView(
  testState: DiagnosticTestState,
  onSelectOption: (Int) -> Unit,
  onSubmitAnswer: () -> Unit,
  onNextQuestion: () -> Unit,
  onDismiss: () -> Unit
) {
  val question = testState.currentQuestion ?: return
  val total = testState.questions.size
  val currentNum = testState.currentIndex + 1
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
      .verticalScroll(scrollState)
  ) {
    // Header Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(RitCobalt.copy(alpha = 0.12f)),
          contentAlignment = Alignment.Center
        ) {
          Text(text = testState.language.flagEmoji, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "${testState.language.name} Diagnostic Test",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = "Assessing your starting proficiency level",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      IconButton(
        onClick = onDismiss,
        modifier = Modifier.testTag("close_diagnostic_dialog")
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Close diagnostic test",
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Progress Bar and Question Number
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Question $currentNum of $total",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.SemiBold,
          color = RitNavyPrimary
        )
      )

      TierBadge(level = question.targetLevel)
    }

    Spacer(modifier = Modifier.height(6.dp))

    LinearProgressIndicator(
      progress = { (currentNum.toFloat() / total.toFloat()).coerceIn(0f, 1f) },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = RitCobalt,
      trackColor = MaterialTheme.colorScheme.surfaceVariant
    )

    Spacer(modifier = Modifier.height(18.dp))

    // Question Prompt Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = RitNavyPrimary.copy(alpha = 0.05f)
      ),
      border = BorderStroke(1.dp, RitNavyPrimary.copy(alpha = 0.15f))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        if (!question.scriptPrompt.isNullOrBlank()) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = RitNavyPrimary.copy(alpha = 0.1f),
            modifier = Modifier.padding(bottom = 10.dp)
          ) {
            Text(
              text = question.scriptPrompt,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary,
                fontFamily = FontFamily.Serif
              )
            )
          }
        }

        Text(
          text = question.prompt,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 22.sp
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Options List
    question.options.forEachIndexed { index, optionText ->
      val isSelected = testState.selectedOptionIndex == index
      val isCorrect = index == question.correctOptionIndex
      val isRevealed = testState.isAnswerRevealed

      val containerColor = when {
        isRevealed && isCorrect -> SuccessGreen.copy(alpha = 0.12f)
        isRevealed && isSelected && !isCorrect -> SoftRed.copy(alpha = 0.12f)
        isSelected -> RitCobalt.copy(alpha = 0.08f)
        else -> MaterialTheme.colorScheme.surface
      }

      val borderColor = when {
        isRevealed && isCorrect -> SuccessGreen
        isRevealed && isSelected && !isCorrect -> SoftRed
        isSelected -> RitCobalt
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
      }

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 5.dp)
          .clickable(enabled = !isRevealed) { onSelectOption(index) }
          .testTag("diagnostic_option_$index"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || (isRevealed && isCorrect)) 2.dp else 1.dp, borderColor)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Option Letter Badge (A, B, C, D)
          val optionLetter = ('A' + index).toString()
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(
                when {
                  isRevealed && isCorrect -> SuccessGreen
                  isRevealed && isSelected && !isCorrect -> SoftRed
                  isSelected -> RitCobalt
                  else -> MaterialTheme.colorScheme.surfaceVariant
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = optionLetter,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected || isRevealed && (isCorrect || isSelected)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Text(
            text = optionText,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (isSelected || (isRevealed && isCorrect)) FontWeight.SemiBold else FontWeight.Normal,
              color = MaterialTheme.colorScheme.onSurface
            )
          )

          if (isRevealed) {
            if (isCorrect) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Correct Answer",
                tint = SuccessGreen,
                modifier = Modifier.size(20.dp)
              )
            } else if (isSelected) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Incorrect Answer",
                tint = SoftRed,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    }

    // Explanation Banner when revealed
    AnimatedVisibility(
      visible = testState.isAnswerRevealed,
      enter = fadeIn() + slideInVertically()
    ) {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp),
        shape = RoundedCornerShape(12.dp),
        color = RitCobalt.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, RitCobalt.copy(alpha = 0.3f))
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = RitCobalt,
            modifier = Modifier
              .size(20.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "Proficiency Insight",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RitCobalt
              )
            )
            Text(
              text = question.explanation,
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
              )
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Action Button: Check Answer or Continue
    if (!testState.isAnswerRevealed) {
      Button(
        onClick = onSubmitAnswer,
        enabled = testState.selectedOptionIndex != null,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("diagnostic_check_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = RitNavyPrimary,
          contentColor = Color.White
        )
      ) {
        Text(
          text = "Check Answer",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
      }
    } else {
      Button(
        onClick = onNextQuestion,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("diagnostic_next_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (testState.isLastQuestion) SuccessGreen else RitCobalt,
          contentColor = Color.White
        )
      ) {
        Text(
          text = if (testState.isLastQuestion) "View Diagnostic Results 🎉" else "Next Question →",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}

@Composable
private fun DiagnosticResultView(
  result: DiagnosticTestResult,
  onRetake: () -> Unit,
  onConfirm: () -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp)
      .verticalScroll(scrollState),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "🎉 Assessment Complete!",
      style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold,
        color = RitNavyPrimary
      )
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Diagnostic Evaluation for ${result.languageName}",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Big Level Badge Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("diagnostic_result_card"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(
        containerColor = when (result.assessedLevel) {
          ProficiencyLevel.BEGINNER -> RitTeal.copy(alpha = 0.08f)
          ProficiencyLevel.INTERMEDIATE -> RitCobalt.copy(alpha = 0.08f)
          ProficiencyLevel.ADVANCED -> RitGoldAccent.copy(alpha = 0.12f)
        }
      ),
      border = BorderStroke(
        width = 2.dp,
        color = when (result.assessedLevel) {
          ProficiencyLevel.BEGINNER -> RitTeal
          ProficiencyLevel.INTERMEDIATE -> RitCobalt
          ProficiencyLevel.ADVANCED -> RitGoldAccent
        }
      )
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = result.assessedLevel.iconEmoji,
          fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Assessed Level: ${result.assessedLevel.title}",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = RitNavyPrimary,
          modifier = Modifier.padding(vertical = 6.dp)
        ) {
          Text(
            text = "CEFR ${result.assessedLevel.shortLabel}",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = result.assessedLevel.description,
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Score summary chip
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surface,
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Accuracy: ${result.score} / ${result.totalQuestions} (${result.percentage}%)",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary
              )
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Learning Pathway Guidance
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = RitGoldAccent,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Tailored Learning Pathway",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = result.feedback,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Next Steps: ${result.assessedLevel.nextSteps}",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = RitCobalt
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // XP Reward Banner
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = RitGoldAccent.copy(alpha = 0.15f),
      border = BorderStroke(1.dp, RitGoldAccent.copy(alpha = 0.5f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = "🏆 +${result.xpAwarded} Diagnostic XP Bonus Awarded!",
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Bold,
            color = RitNavyPrimary
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Action buttons
    Button(
      onClick = onConfirm,
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("confirm_proficiency_button"),
      shape = RoundedCornerShape(12.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = RitNavyPrimary,
        contentColor = Color.White
      )
    ) {
      Text(
        text = "Start Learning ${result.languageName}",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedButton(
      onClick = onRetake,
      modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .testTag("retake_proficiency_button"),
      shape = RoundedCornerShape(12.dp),
      border = BorderStroke(1.dp, RitCobalt)
    ) {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = "Retake Assessment",
        tint = RitCobalt,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "Retake Diagnostic Test",
        style = MaterialTheme.typography.labelMedium.copy(
          color = RitCobalt,
          fontWeight = FontWeight.SemiBold
        )
      )
    }
  }
}

@Composable
private fun TierBadge(level: ProficiencyLevel) {
  val (label, bg, fg) = when (level) {
    ProficiencyLevel.BEGINNER -> Triple("Beginner", RitTeal.copy(alpha = 0.12f), RitTeal)
    ProficiencyLevel.INTERMEDIATE -> Triple("Intermediate", RitCobalt.copy(alpha = 0.12f), RitCobalt)
    ProficiencyLevel.ADVANCED -> Triple("Advanced", RitGoldAccent.copy(alpha = 0.2f), RitNavyPrimary)
  }

  Surface(
    shape = RoundedCornerShape(6.dp),
    color = bg
  ) {
    Text(
      text = "$label Tier",
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        color = fg
      )
    )
  }
}
