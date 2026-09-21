package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserLearningStats
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

data class DayStreakInfo(
  val dayAbbr: String,    // "M", "T", "W", "T", "F", "S", "S"
  val dateNumber: Int,    // 21
  val dateString: String,  // "2026-09-21"
  val isToday: Boolean,
  val isLogged: Boolean,
  val isPracticed: Boolean
)

@Composable
fun DailyStreakCard(
  userStats: UserLearningStats,
  weeklyStreakList: List<DayStreakInfo>,
  todayPracticed: Boolean,
  onPracticeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "flame_pulse")
  val flameScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "flame_scale"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("daily_streak_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.5.dp, RitGoldAccent.copy(alpha = 0.6f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header: Flame Icon, Streak Count, Personal Best
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .scale(flameScale)
              .clip(CircleShape)
              .background(
                Brush.radialGradient(
                  colors = listOf(RitGoldAccent, WarningAmber, RitNavyDark)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.LocalFireDepartment,
              contentDescription = "Daily Streak Flame",
              tint = Color.White,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "${userStats.streakDays} Day Streak",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = RitNavyDark
                ),
                modifier = Modifier.testTag("streak_count_text")
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "🔥", fontSize = 16.sp)
            }
            Text(
              text = "Daily Practice Commitment • RIT Autonomous",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        // Best Record Pill
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = RitGoldAccent.copy(alpha = 0.18f),
          border = BorderStroke(1.dp, RitGoldAccent.copy(alpha = 0.4f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = "Best Streak",
              tint = WarningAmber,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Best: ${userStats.bestStreakDays}d",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyDark
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 7-Day Weekly Calendar Progress Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("weekly_streak_row"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        weeklyStreakList.forEach { day ->
          DayStreakItem(day = day)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Status message & Action
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (todayPracticed) SuccessGreen.copy(alpha = 0.1f) else RitCobalt.copy(alpha = 0.08f),
        border = BorderStroke(
          1.dp,
          if (todayPracticed) SuccessGreen.copy(alpha = 0.3f) else RitCobalt.copy(alpha = 0.2f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = if (todayPracticed) Icons.Default.Check else Icons.Default.Shield,
              contentDescription = null,
              tint = if (todayPracticed) SuccessGreen else RitCobalt,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (todayPracticed) "Streak Protected for Today!" else "Practice 1 phrase to keep streak!",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (todayPracticed) SuccessGreen else RitNavyDark
                )
              )
              Text(
                text = if (todayPracticed) "+25 XP Daily Login Bonus Awarded" else "Speaking or cultural exploration counts",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          if (!todayPracticed) {
            Button(
              onClick = onPracticeClick,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = RitNavyPrimary,
                contentColor = Color.White
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
              modifier = Modifier
                .height(34.dp)
                .testTag("practice_now_streak_btn")
            ) {
              Icon(Icons.Default.Mic, contentDescription = "Practice", modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Practice", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
          } else {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = SuccessGreen.copy(alpha = 0.2f)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                  text = "Active",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SuccessGreen,
                    fontSize = 10.sp
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

@Composable
fun DayStreakItem(
  day: DayStreakInfo,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.testTag("day_streak_${day.dayAbbr}_${day.dateNumber}")
  ) {
    Text(
      text = day.dayAbbr,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 11.sp,
        fontWeight = if (day.isToday) FontWeight.ExtraBold else FontWeight.Medium,
        color = if (day.isToday) RitNavyPrimary else MaterialTheme.colorScheme.onSurfaceVariant
      )
    )

    Spacer(modifier = Modifier.height(4.dp))

    Box(
      modifier = Modifier
        .size(34.dp)
        .clip(CircleShape)
        .background(
          when {
            day.isLogged && day.isPracticed -> SuccessGreen
            day.isLogged -> RitGoldAccent
            day.isToday -> RitNavyPrimary.copy(alpha = 0.12f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          }
        )
        .then(
          if (day.isToday) {
            Modifier.background(
              color = Color.Transparent,
              shape = CircleShape
            )
          } else Modifier
        ),
      contentAlignment = Alignment.Center
    ) {
      if (day.isLogged) {
        Icon(
          imageVector = if (day.isPracticed) Icons.Default.Check else Icons.Default.LocalFireDepartment,
          contentDescription = null,
          tint = if (day.isPracticed) Color.White else RitNavyDark,
          modifier = Modifier.size(18.dp)
        )
      } else {
        Text(
          text = "${day.dateNumber}",
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 11.sp,
            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
            color = if (day.isToday) RitNavyPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(2.dp))

    if (day.isToday) {
      Box(
        modifier = Modifier
          .size(4.dp)
          .clip(CircleShape)
          .background(RitGoldAccent)
      )
    } else {
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}
