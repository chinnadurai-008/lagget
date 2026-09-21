package com.example.data.model

enum class CulturalCategory(val title: String, val icon: String, val description: String) {
  GREETINGS(
    "Greetings & Hierarchy",
    "🤝",
    "Body language, bowing, handshakes, and levels of politeness"
  ),
  DINING_ETIQUETTE(
    "Dining & Gastronomy",
    "🍽️",
    "Table manners, utensils, sharing dishes, toasts, and tipping"
  ),
  COMMON_IDIOMS(
    "Idioms & Proverbs",
    "💬",
    "Colorful local expressions, untranslatable wisdom, and hidden meanings"
  ),
  HOLIDAYS_FESTIVALS(
    "Holidays & Celebrations",
    "🎉",
    "Major cultural milestones, seasonal festivals, rituals, and seasonal greetings"
  ),
  TABOOS_CUSTOMS(
    "Taboos & Daily Life",
    "🛡️",
    "Social courtesies, sacred spaces, gift giving, and faux pas to avoid"
  )
}

data class CulturalInsightItem(
  val id: String,
  val languageId: String,
  val category: CulturalCategory,
  val title: String,
  val originalPhrase: String? = null,
  val phonetic: String? = null,
  val translation: String? = null,
  val culturalContext: String,
  val etiquetteDo: String,
  val etiquetteDont: String,
  val significance: String,
  val iconEmoji: String = "🌟"
)
