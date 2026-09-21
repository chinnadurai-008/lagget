package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CulturalCategory
import com.example.data.model.CulturalInsightItem
import com.example.data.model.Language
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SoftRed
import com.example.ui.theme.SuccessGreen

@Composable
fun CulturalInsightsScreen(
  selectedLanguage: Language,
  insights: List<CulturalInsightItem>,
  selectedCategory: CulturalCategory?,
  bookmarkedIds: Set<String>,
  isGeneratingInsight: Boolean,
  statusMessage: String?,
  onCategorySelected: (CulturalCategory?) -> Unit,
  onToggleBookmark: (CulturalInsightItem) -> Unit,
  onPlayAudio: (String, String) -> Unit,
  onGenerateCustomInsight: (String, String) -> Unit,
  onGoToSpeakingPractice: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showAiCustomDialog by remember { mutableStateOf(false) }
  var customQuery by remember { mutableStateOf("") }
  var selectedAiTopic by remember { mutableStateOf("Campus & Student Life") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("cultural_insights_screen")
  ) {
    Spacer(modifier = Modifier.height(12.dp))

    // Language Banner
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = RitNavyPrimary.copy(alpha = 0.07f),
      border = BorderStroke(1.dp, RitNavyPrimary.copy(alpha = 0.2f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Text(text = selectedLanguage.flagEmoji, fontSize = 26.sp)
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "${selectedLanguage.name} Cultural Modules",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary
              )
            )
            Text(
              text = "Ramco Institute of Technology Language Curriculum",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
              )
            )
          }
        }

        // Button to trigger AI generation
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = RitGoldAccent,
          modifier = Modifier
            .testTag("toggle_ai_custom_insight_btn")
        ) {
          Button(
            onClick = { showAiCustomDialog = !showAiCustomDialog },
            colors = ButtonDefaults.buttonColors(
              containerColor = RitGoldAccent,
              contentColor = RitNavyDark
            ),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.height(34.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = "AI Culture",
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (showAiCustomDialog) "Close AI" else "+ Ask AI",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Expandable AI Query Card
    AnimatedVisibility(visible = showAiCustomDialog) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp)
          .testTag("ai_custom_culture_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, RitGoldAccent),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(RitGoldAccent.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = RitNavyPrimary,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "AI Real-Time Cultural Insight Generator",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Generate real-time authentic context for ${selectedLanguage.name} powered by Gemini:",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Preset suggestion chips
          val presetTopics = listOf(
            "Campus & Student Life",
            "Dining & Tea Etiquette",
            "Greetings & Hierarchy",
            "Common Idioms",
            "Festivals & Traditions",
            "Business Etiquette"
          )
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            presetTopics.forEach { topic ->
              val isSelected = selectedAiTopic == topic
              FilterChip(
                selected = isSelected,
                onClick = { selectedAiTopic = topic },
                label = { Text(topic, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = RitCobalt,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = customQuery,
            onValueChange = { customQuery = it },
            placeholder = {
              Text(
                "E.g., What should a guest bring to a family dinner in ${selectedLanguage.name} culture?",
                fontSize = 12.sp
              )
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("ai_custom_query_input"),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = RitNavyPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            singleLine = false,
            maxLines = 3
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
          ) {
            if (isGeneratingInsight) {
              CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = RitNavyPrimary,
                strokeWidth = 2.5.dp
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = statusMessage ?: "Synthesizing cultural wisdom...",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = RitNavyPrimary,
                  fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
              )
            } else {
              Button(
                onClick = {
                  val queryToSend = if (customQuery.isBlank()) "Explain key nuances of $selectedAiTopic" else customQuery
                  onGenerateCustomInsight(selectedAiTopic, queryToSend)
                  customQuery = ""
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = RitNavyPrimary,
                  contentColor = Color.White
                ),
                modifier = Modifier.testTag("submit_ai_cultural_query_btn")
              ) {
                Icon(Icons.Default.Send, contentDescription = "Submit", modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate Cultural Module", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
              }
            }
          }
        }
      }
    }

    // Category Selector Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // "All" chip
      FilterChip(
        selected = selectedCategory == null,
        onClick = { onCategorySelected(null) },
        label = {
          Text(
            text = "All Topics (${insights.size})",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = if (selectedCategory == null) FontWeight.Bold else FontWeight.Normal
            )
          )
        },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = RitNavyPrimary,
          selectedLabelColor = Color.White
        )
      )

      CulturalCategory.values().forEach { cat ->
        val isSelected = selectedCategory == cat
        FilterChip(
          selected = isSelected,
          onClick = { onCategorySelected(cat) },
          label = {
            Text(
              text = "${cat.icon} ${cat.title}",
              style = MaterialTheme.typography.labelMedium.copy(
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

    Spacer(modifier = Modifier.height(10.dp))

    // Cultural Insight Modules List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      items(insights, key = { it.id }) { item ->
        val isBookmarked = bookmarkedIds.contains(item.id)
        CulturalInsightCard(
          item = item,
          language = selectedLanguage,
          isBookmarked = isBookmarked,
          onToggleBookmark = { onToggleBookmark(item) },
          onPlayAudio = { text -> onPlayAudio(text, selectedLanguage.ttsLocaleCode) },
          onPracticeSpeaking = onGoToSpeakingPractice
        )
      }
    }
  }
}

@Composable
fun CulturalInsightCard(
  item: CulturalInsightItem,
  language: Language,
  isBookmarked: Boolean,
  onToggleBookmark: () -> Unit,
  onPlayAudio: (String) -> Unit,
  onPracticeSpeaking: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("cultural_card_${item.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header: Category Tag & Bookmark
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = when (item.category) {
            CulturalCategory.GREETINGS -> RitCobalt.copy(alpha = 0.12f)
            CulturalCategory.DINING_ETIQUETTE -> RitGoldAccent.copy(alpha = 0.18f)
            CulturalCategory.COMMON_IDIOMS -> RitTeal.copy(alpha = 0.15f)
            CulturalCategory.HOLIDAYS_FESTIVALS -> Color(0xFFFDE047).copy(alpha = 0.25f)
            CulturalCategory.TABOOS_CUSTOMS -> SoftRed.copy(alpha = 0.12f)
          }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = item.category.icon, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = item.category.title.uppercase(),
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp,
                color = RitNavyPrimary
              )
            )
          }
        }

        IconButton(
          onClick = onToggleBookmark,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (isBookmarked) "Remove Bookmark" else "Bookmark",
            tint = if (isBookmarked) RitGoldAccent else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Title
      Text(
        text = item.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      // Native Phrase and Pronunciation Box
      if (item.originalPhrase != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = item.originalPhrase,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyPrimary
                )
              )
              if (item.phonetic != null) {
                Text(
                  text = "Pronunciation: ${item.phonetic}",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                  )
                )
              }
              if (item.translation != null) {
                Text(
                  text = "“${item.translation}”",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = RitTeal,
                    fontSize = 12.sp
                  )
                )
              }
            }

            IconButton(
              onClick = { onPlayAudio(item.originalPhrase) },
              modifier = Modifier
                .size(36.dp)
                .testTag("play_audio_${item.id}")
            ) {
              Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Listen Phrase",
                tint = RitNavyPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Deep Cultural Context Description
      Text(
        text = item.culturalContext,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurface,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Etiquette Guidance: DO vs DON'T
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // DO Card
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SuccessGreen.copy(alpha = 0.08f),
          border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Do",
              tint = SuccessGreen,
              modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "CULTURAL DO",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 10.sp,
                  color = SuccessGreen
                )
              )
              Text(
                text = item.etiquetteDo,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 12.sp
                )
              )
            }
          }
        }

        // DON'T Card
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = SoftRed.copy(alpha = 0.08f),
          border = BorderStroke(1.dp, SoftRed.copy(alpha = 0.3f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Don't",
              tint = SoftRed,
              modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "CULTURAL TABOO (DON'T)",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 10.sp,
                  color = SoftRed
                )
              )
              Text(
                text = item.etiquetteDont,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 12.sp
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Footer: Significance and Speaking Practice Link
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "💡", fontSize = 12.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = item.significance,
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 11.sp
            ),
            maxLines = 2
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = RitTeal.copy(alpha = 0.15f),
          modifier = Modifier.clickable { onPracticeSpeaking() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = "Practice Speaking",
              tint = RitNavyPrimary,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "Practice",
              style = MaterialTheme.typography.labelSmall.copy(
                color = RitNavyPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            )
          }
        }
      }
    }
  }
}
