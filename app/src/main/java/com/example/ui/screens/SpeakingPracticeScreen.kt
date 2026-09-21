package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SpeakingHistoryEntity
import com.example.data.model.Language
import com.example.data.model.SpeakingEvaluation
import com.example.data.model.SpeakingExercise
import com.example.data.repository.LanguageRepository
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SoftRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@Composable
fun SpeakingPracticeScreen(
  selectedLanguage: Language,
  selectedExercise: SpeakingExercise?,
  isListening: Boolean,
  spokenTranscript: String,
  isEvaluatingSpeech: Boolean,
  evaluation: SpeakingEvaluation?,
  historyList: List<SpeakingHistoryEntity>,
  audioRmsLevel: Float,
  onSelectExercise: (SpeakingExercise) -> Unit,
  onPlayAudio: (SpeakingExercise) -> Unit,
  onStartListening: (SpeakingExercise) -> Unit,
  onStopListening: () -> Unit,
  onManualEvaluate: (SpeakingExercise, String) -> Unit,
  modifier: Modifier = Modifier
) {
  val exercises = LanguageRepository.getSpeakingExercisesForLanguage(selectedLanguage.id)
  var manualInputText by remember { mutableStateOf("") }
  var showManualInput by remember { mutableStateOf(false) }

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isListening) 1.25f else 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "mic_pulse"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("speaking_practice_screen"),
    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 0.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header banner
    item {
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = RitNavyPrimary,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = selectedLanguage.flagEmoji, fontSize = 22.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "${selectedLanguage.name} AI Speaking Coach",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              )
            }
            Text(
              text = "Ramco Institute of Technology AI Linguistic Lab",
              style = MaterialTheme.typography.bodySmall.copy(
                color = RitGoldAccent,
                fontSize = 11.sp
              )
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White.copy(alpha = 0.15f)
          ) {
            Text(
              text = "Gemini Powered",
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            )
          }
        }
      }
    }

    // Exercise scenario selector
    item {
      Column {
        Text(
          text = "Select Speaking Scenario:",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(exercises, key = { it.id }) { ex ->
            val isSelected = selectedExercise?.id == ex.id
            FilterChip(
              selected = isSelected,
              onClick = { onSelectExercise(ex) },
              label = {
                Column(modifier = Modifier.padding(vertical = 2.dp)) {
                  Text(
                    text = ex.scenario,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                  )
                  Text(
                    text = ex.level,
                    fontSize = 9.sp,
                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
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

    // Active Exercise Card
    if (selectedExercise != null) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("active_speaking_card"),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(1.5.dp, RitCobalt.copy(alpha = 0.4f)),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = RitGoldAccent.copy(alpha = 0.2f)
              ) {
                Text(
                  text = selectedExercise.scenario.uppercase(),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = RitNavyDark
                  )
                )
              }

              // Listen Pronunciation Button
              Button(
                onClick = { onPlayAudio(selectedExercise) },
                colors = ButtonDefaults.buttonColors(
                  containerColor = RitCobalt,
                  contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(32.dp).testTag("listen_phrase_btn")
              ) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Listen", modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Listen", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // The target phrase in large clear typography
            Text(
              text = selectedExercise.phrase,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary,
                lineHeight = 28.sp
              ),
              modifier = Modifier.testTag("target_speech_phrase")
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "Phonetic: ${selectedExercise.phonetic}",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )

            Text(
              text = "Translation: “${selectedExercise.englishTranslation}”",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = RitTeal
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Cultural Usage Tip
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = RitGoldAccent.copy(alpha = 0.12f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "💡", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(
                    text = "CULTURAL USAGE NOTE",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.ExtraBold,
                      color = RitNavyDark,
                      fontSize = 9.sp
                    )
                  )
                  Text(
                    text = selectedExercise.culturalUsageTip,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = RitNavyDark,
                      fontSize = 11.sp
                    )
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interactive Recording Area
            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(76.dp)
                  .scale(pulseScale)
                  .clip(CircleShape)
                  .background(
                    if (isListening) Brush.radialGradient(listOf(SoftRed, RitNavyDark))
                    else Brush.radialGradient(listOf(RitNavyPrimary, RitCobalt))
                  )
                  .clickable {
                    if (isListening) onStopListening() else onStartListening(selectedExercise)
                  }
                  .testTag("mic_record_button"),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                  contentDescription = if (isListening) "Stop Speaking" else "Start Speaking",
                  tint = Color.White,
                  modifier = Modifier.size(36.dp)
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = if (isListening) "Listening... Speak now into microphone!" else "Tap to Speak this phrase",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isListening) SoftRed else RitNavyPrimary
                )
              )

              if (spokenTranscript.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "Recognized: “$spokenTranscript”",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontWeight = FontWeight.Medium,
                      textAlign = TextAlign.Center
                    )
                  )
                }
              }

              // Text input fallback for simulated / quiet environments
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = if (showManualInput) "Hide text input" else "Or type what you said to test",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = RitTeal,
                  fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                  .clickable { showManualInput = !showManualInput }
                  .testTag("toggle_manual_transcript_btn")
              )

              if (showManualInput) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  OutlinedTextField(
                    value = manualInputText,
                    onValueChange = { manualInputText = it },
                    placeholder = { Text("Enter your spoken attempt…", fontSize = 11.sp) },
                    modifier = Modifier
                      .weight(1f)
                      .testTag("manual_speech_input"),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Button(
                    onClick = {
                      if (manualInputText.isNotBlank()) {
                        onManualEvaluate(selectedExercise, manualInputText)
                        manualInputText = ""
                      }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RitNavyPrimary),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Icon(Icons.Default.Send, contentDescription = "Test", modifier = Modifier.size(14.dp))
                  }
                }
              }
            }
          }
        }
      }
    }

    // Evaluation Results Card
    if (isEvaluatingSpeech) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(24.dp),
              color = RitNavyPrimary,
              strokeWidth = 2.5.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "Gemini AI evaluating pronunciation & cultural nuance...",
              style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
            )
          }
        }
      }
    } else if (evaluation != null) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("speech_evaluation_card"),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(
            1.5.dp,
            if (evaluation.score >= 80) SuccessGreen else if (evaluation.score >= 65) WarningAmber else SoftRed
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                      if (evaluation.score >= 80) SuccessGreen.copy(alpha = 0.15f)
                      else if (evaluation.score >= 65) WarningAmber.copy(alpha = 0.15f)
                      else SoftRed.copy(alpha = 0.15f)
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${evaluation.score}",
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.ExtraBold,
                      color = if (evaluation.score >= 80) SuccessGreen else if (evaluation.score >= 65) WarningAmber else SoftRed
                    )
                  )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                  Text(
                    text = "Pronunciation Score",
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                  )
                  Text(
                    text = evaluation.accuracyStatus,
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (evaluation.score >= 80) SuccessGreen else if (evaluation.score >= 65) WarningAmber else SoftRed
                    )
                  )
                }
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = RitGoldAccent.copy(alpha = 0.2f)
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = RitNavyPrimary, modifier = Modifier.size(12.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "+${evaluation.score / 4} XP",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = RitNavyPrimary)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Intonation Feedback
            Text(
              text = "Intonation & Cadence:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = RitNavyPrimary)
            )
            Text(
              text = evaluation.intonationFeedback,
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Pronunciation Tip
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = RitCobalt.copy(alpha = 0.08f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(
                  text = "ACTIONABLE PRONUNCIATION TIP",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = RitNavyPrimary,
                    fontSize = 10.sp
                  )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = evaluation.pronunciationTip,
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cultural Nuance Note
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = RitTeal.copy(alpha = 0.1f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(
                  text = "CULTURAL NUANCE",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = RitTeal,
                    fontSize = 10.sp
                  )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = evaluation.culturalNuanceNote,
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                )
              }
            }
          }
        }
      }
    }

    // Recent Practice History
    if (historyList.isNotEmpty()) {
      item {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
          ) {
            Icon(Icons.Default.History, contentDescription = "History", tint = RitNavyPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Recent Practice Attempts (${historyList.size})",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
        }
      }

      items(historyList.take(5), key = { it.id }) { item ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = item.targetPhrase,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
              )
              Text(
                text = "Spoke: “${item.spokenText}”",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontStyle = FontStyle.Italic,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (item.score >= 80) SuccessGreen.copy(alpha = 0.2f) else WarningAmber.copy(alpha = 0.2f)
            ) {
              Text(
                text = "${item.score}%",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (item.score >= 80) SuccessGreen else WarningAmber
                )
              )
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(60.dp))
    }
  }
}
