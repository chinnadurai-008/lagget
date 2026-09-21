package com.example.data.model

data class Language(
  val id: String,
  val name: String,
  val nativeName: String,
  val flagEmoji: String,
  val scriptName: String,
  val region: String,
  val approximateSpeakers: String,
  val formalGreeting: String,
  val greetingTranslation: String,
  val ttsLocaleCode: String,
  val culturalHighlight: String,
  val mottoOrProverb: String
)

enum class WorldRegion(val displayName: String) {
  ALL("All Continents"),
  ASIA_INDIA("Asia & India"),
  EUROPE("Europe"),
  AMERICAS("Americas"),
  AFRICA_MIDDLE_EAST("Africa & Middle East")
}
