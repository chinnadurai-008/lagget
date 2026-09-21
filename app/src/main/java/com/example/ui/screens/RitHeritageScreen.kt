package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal

@Composable
fun RitHeritageScreen(
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("rit_heritage_screen"),
    contentPadding = PaddingValues(vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Institution Crest Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, RitGoldAccent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                listOf(RitNavyDark, RitNavyPrimary)
              )
            )
            .padding(20.dp)
        ) {
          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(RitGoldAccent),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.School,
                contentDescription = "RIT Crest",
                tint = RitNavyDark,
                modifier = Modifier.size(38.dp)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = "RAMCO INSTITUTE OF TECHNOLOGY",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = RitGoldAccent,
                letterSpacing = 1.sp
              )
            )

            Text(
              text = "(An Autonomous Institution)",
              style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold
              )
            )

            Text(
              text = "Approved by AICTE, Affiliated to Anna University, Chennai\nRajapalayam, Tamil Nadu - 626117",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 11.sp,
                lineHeight = 16.sp
              ),
              modifier = Modifier.padding(top = 4.dp),
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color.White.copy(alpha = 0.15f)
            ) {
              Text(
                text = "Lagget: The Global Language & Cultural Initiative",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              )
            }
          }
        }
      }
    }

    // Vision and Mission
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Public, contentDescription = null, tint = RitNavyPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "The Vision of Lagget",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Lagget was envisioned at Ramco Institute of Technology (Autonomous) to bridge world cultures through engineering excellence and artificial intelligence. By unifying all world languages into a single intelligent platform, RIT empowers students and global citizens with speaking fluency and deep anthropological etiquette.",
            style = MaterialTheme.typography.bodyMedium.copy(
              lineHeight = 22.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }
      }
    }

    // Tamil Heritage & Sangam Foundation
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, RitGoldAccent.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Book, contentDescription = null, tint = RitCobalt, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Rooted in Tamil Classical Heritage",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = RitGoldAccent.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "“யாதும் ஊரே யாவரும் கேளிர்”",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = RitNavyDark
                )
              )
              Text(
                text = "“To us all towns are our homeland, all mankind is our kin”",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontStyle = FontStyle.Italic,
                  color = RitNavyPrimary
                )
              )
              Text(
                text = "— Kaniyan Pungundranar, Sangam Era Purananuru (c. 6th century BCE)",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 10.sp
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "From our autonomous campus in the historic cotton town of Rajapalayam, this Sangam philosophy inspires Lagget: no language is foreign when approached with humility, empathetic listening, and cultural respect.",
            style = MaterialTheme.typography.bodySmall.copy(
              lineHeight = 20.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }
      }
    }

    // Key Features of the Initiative
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Core Pillars of Lagget",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = RitNavyPrimary)
          )

          Spacer(modifier = Modifier.height(10.dp))

          val pillars = listOf(
            Triple("🌐 All World Languages", "Comprehensive repository linking phonetic transcripts, grammatical cadence, and cultural proverbs across all continents.", RitNavyPrimary),
            Triple("🎙️ AI Speaking Evaluation", "Interactive microphone input evaluated in real-time by Gemini AI for intonation, phonetic fidelity, and contextual appropriateness.", RitCobalt),
            Triple("🏛️ Dynamic Cultural Modules", "Etiquette, dining rituals, taboos, idioms, and seasonal celebrations that prevent inadvertent cross-cultural misunderstandings.", RitTeal),
            Triple("🎓 Autonomous Innovation", "Designed and deployed by students and faculty at Ramco Institute of Technology for global leadership.", RitGoldAccent)
          )

          pillars.forEach { (title, desc, color) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .padding(top = 6.dp)
                  .clip(CircleShape)
                  .background(color)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(text = title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Text(text = desc, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp))
              }
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
