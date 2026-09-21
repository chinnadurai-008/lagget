package com.example.data.model

enum class ProficiencyLevel(
  val title: String,
  val shortLabel: String,
  val iconEmoji: String,
  val description: String,
  val nextSteps: String
) {
  BEGINNER(
    title = "Beginner",
    shortLabel = "A1 - A2",
    iconEmoji = "🌱",
    description = "Foundational grasp of essential greetings, core vocabulary, and phonetics.",
    nextSteps = "We will focus on foundational vocabulary, audio pronunciation, and everyday pleasantries."
  ),
  INTERMEDIATE(
    title = "Intermediate",
    shortLabel = "B1 - B2",
    iconEmoji = "🌿",
    description = "Comfortable with situational conversations, verb conjugations, and cultural contexts.",
    nextSteps = "We will advance to dialogue flow, idioms, and interactive speaking exercises."
  ),
  ADVANCED(
    title = "Advanced",
    shortLabel = "C1 - C2",
    iconEmoji = "🌳",
    description = "High proficiency with nuanced idiomatic expressions, formal registers, and complex cultural discussions.",
    nextSteps = "We will focus on native-like cadence, classical literature, and complex regional idioms."
  );

  companion object {
    fun fromScorePercentage(percentage: Int): ProficiencyLevel {
      return when {
        percentage >= 75 -> ADVANCED
        percentage >= 45 -> INTERMEDIATE
        else -> BEGINNER
      }
    }
  }
}

data class DiagnosticQuestion(
  val id: String,
  val languageId: String,
  val prompt: String,
  val scriptPrompt: String? = null,
  val options: List<String>,
  val correctOptionIndex: Int,
  val targetLevel: ProficiencyLevel,
  val explanation: String
)

data class DiagnosticTestResult(
  val languageId: String,
  val languageName: String,
  val score: Int,
  val totalQuestions: Int,
  val percentage: Int,
  val assessedLevel: ProficiencyLevel,
  val feedback: String,
  val xpAwarded: Int = 50
)

data class DiagnosticTestState(
  val language: Language,
  val questions: List<DiagnosticQuestion>,
  val currentIndex: Int = 0,
  val selectedOptionIndex: Int? = null,
  val isAnswerRevealed: Boolean = false,
  val userAnswers: Map<Int, Int> = emptyMap(), // questionIndex -> selectedOption
  val isCompleted: Boolean = false,
  val result: DiagnosticTestResult? = null
) {
  val currentQuestion: DiagnosticQuestion?
    get() = questions.getOrNull(currentIndex)

  val isLastQuestion: Boolean
    get() = currentIndex == questions.size - 1

  val progressFraction: Float
    get() = if (questions.isEmpty()) 0f else (currentIndex + 1).toFloat() / questions.size.toFloat()
}
