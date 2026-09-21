package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AchievementBadge
import com.example.data.model.BadgeTier
import com.example.ui.theme.RitCobalt
import com.example.ui.theme.RitGoldAccent
import com.example.ui.theme.RitNavyDark
import com.example.ui.theme.RitNavyPrimary
import com.example.ui.theme.RitTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@Composable
fun AchievementBadgeCard(
  badge: AchievementBadge,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val tierColor = when (badge.tier) {
    BadgeTier.BRONZE -> Color(0xFFCD7F32)
    BadgeTier.SILVER -> Color(0xFFA8B2BC)
    BadgeTier.GOLD -> RitGoldAccent
    BadgeTier.PLATINUM -> RitTeal
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("badge_card_${badge.id}")
      .clickable { onClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (badge.isUnlocked) {
        MaterialTheme.colorScheme.surface
      } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
      }
    ),
    border = BorderStroke(
      width = if (badge.isUnlocked) 1.5.dp else 1.dp,
      color = if (badge.isUnlocked) tierColor.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (badge.isUnlocked) 2.dp else 0.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Medallion Circle
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(
              if (badge.isUnlocked) {
                Brush.radialGradient(
                  colors = listOf(tierColor.copy(alpha = 0.4f), tierColor.copy(alpha = 0.15f), Color.Transparent)
                )
              } else {
                Brush.radialGradient(
                  colors = listOf(Color.Gray.copy(alpha = 0.2f), Color.Transparent)
                )
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          Surface(
            modifier = Modifier.size(42.dp),
            shape = CircleShape,
            color = if (badge.isUnlocked) RitNavyPrimary else MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.5.dp, if (badge.isUnlocked) tierColor else Color.Gray.copy(alpha = 0.4f))
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = badge.iconEmoji,
                fontSize = 22.sp
              )
              if (!badge.isUnlocked) {
                Box(
                  modifier = Modifier
                    .size(42.dp)
                    .background(Color.Black.copy(alpha = 0.35f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked badge",
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(16.dp)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Badge Info
        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = badge.title,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (badge.isUnlocked) RitNavyDark else MaterialTheme.colorScheme.onSurfaceVariant
              ),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )

            // Tier & XP Pill
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = tierColor.copy(alpha = 0.15f),
              border = BorderStroke(0.8.dp, tierColor.copy(alpha = 0.4f))
            ) {
              Text(
                text = "${badge.tier.label} • +${badge.xpReward} XP",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = if (badge.isUnlocked) RitNavyDark else Color.Gray
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = badge.description,
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Progress Bar & Unlock Status
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(modifier = Modifier.weight(1f)) {
          LinearProgressIndicator(
            progress = { badge.progressFraction },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = if (badge.isUnlocked) tierColor else RitCobalt,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        if (badge.isUnlocked) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Unlocked",
              tint = SuccessGreen,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = badge.unlockedDate ?: "Unlocked",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SuccessGreen
              )
            )
          }
        } else {
          Text(
            text = "${badge.currentProgress}/${badge.targetMilestone}",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }
    }
  }
}

@Composable
fun BadgeCelebrationBanner(
  badge: AchievementBadge,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("badge_celebration_banner"),
    shape = RoundedCornerShape(14.dp),
    color = RitNavyDark,
    border = BorderStroke(1.5.dp, RitGoldAccent),
    shadowElevation = 6.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(RitGoldAccent.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = badge.iconEmoji, fontSize = 24.sp)
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = RitGoldAccent,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "NEW BADGE UNLOCKED!",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = RitGoldAccent,
              fontSize = 10.sp
            )
          )
        }
        Text(
          text = badge.title,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
        Text(
          text = "+${badge.xpReward} XP awarded • RIT Academic Honor",
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.8f)
          )
        )
      }

      IconButton(onClick = onDismiss) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Dismiss",
          tint = Color.White.copy(alpha = 0.7f),
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Composable
fun BadgeDetailDialog(
  badge: AchievementBadge,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Close", color = RitNavyPrimary, fontWeight = FontWeight.Bold)
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = badge.iconEmoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = badge.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = RitNavyDark
            )
          )
          Text(
            text = "${badge.category.displayName} • ${badge.tier.label} Tier",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }
    },
    text = {
      Column {
        Text(
          text = badge.description,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "Milestone Criteria:",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = RitNavyDark
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = badge.requirementHint.ifBlank { "Achieve ${badge.targetMilestone} in ${badge.category.displayName} activities." },
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Progress: ${badge.currentProgress}/${badge.targetMilestone}",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = if (badge.isUnlocked) "Status: Unlocked" else "Status: In Progress",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (badge.isUnlocked) SuccessGreen else WarningAmber
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = RitGoldAccent,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Reward: +${badge.xpReward} XP & RIT Autonomous Scholar Badge",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold,
              color = RitNavyPrimary
            )
          )
        }
      }
    }
  )
}
