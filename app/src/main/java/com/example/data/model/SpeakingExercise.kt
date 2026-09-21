package com.example.data.model

data class SpeakingExercise(
  val id: String,
  val languageId: String,
  val scenario: String,
  val level: String,
  val phrase: String,
  val phonetic: String,
  val englishTranslation: String,
  val culturalUsageTip: String,
  val audioLocaleTag: String
)

data class SpeakingEvaluation(
  val score: Int,
  val spokenText: String,
  val accuracyStatus: String,
  val intonationFeedback: String,
  val pronunciationTip: String,
  val culturalNuanceNote: String
)
