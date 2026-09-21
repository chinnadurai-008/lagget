package com.example.data.repository

import com.example.data.model.DiagnosticQuestion
import com.example.data.model.Language
import com.example.data.model.ProficiencyLevel

object DiagnosticTestRepository {

  private val curatedQuestions: Map<String, List<DiagnosticQuestion>> = mapOf(
    "ta" to listOf(
      DiagnosticQuestion(
        id = "ta_q1",
        languageId = "ta",
        prompt = "What is the traditional, respectful Tamil greeting offered with folded hands?",
        scriptPrompt = "வணக்கம்",
        options = listOf("வணக்கம் (Vanakkam)", "நன்றி (Nandri)", "வாருங்கள் (Vaarungal)", "மன்னிக்கவும் (Mannikkavum)"),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'வணக்கம்' (Vanakkam) is the universal reverent Tamil greeting used across Tamil Nadu and global diaspora."
      ),
      DiagnosticQuestion(
        id = "ta_q2",
        languageId = "ta",
        prompt = "Which phrase expresses sincere gratitude ('Thank you') in Tamil?",
        scriptPrompt = "நன்றி",
        options = listOf("ஆகட்டும் (Aagattum)", "நன்றி (Nandri)", "பொறுங்கள் (Porungal)", "போய் வருகிறேன் (Poi varugiren)"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'நன்றி' (Nandri) is the standard term for expressing thankfulness and gratitude."
      ),
      DiagnosticQuestion(
        id = "ta_q3",
        languageId = "ta",
        prompt = "In everyday Tamil social etiquette, what does 'சாப்பிட்டீர்களா?' (Saapitteergala?) express?",
        scriptPrompt = "சாப்பிட்டீர்களா?",
        options = listOf("Where are you going?", "Have you eaten? (A caring, customary social check)", "What is the time?", "Are you ready to leave?"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "Asking whether someone has eaten is a fundamental cultural expression of hospitality and care in Tamil society."
      ),
      DiagnosticQuestion(
        id = "ta_q4",
        languageId = "ta",
        prompt = "The ethical masterwork Thirukkural places paramount value on 'அன்பு' (Anbu). What does this mean?",
        scriptPrompt = "அன்புடமை",
        options = listOf("Discipline", "Love, empathy, and universal benevolence", "Wealth and commerce", "Military bravery"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'அன்பு' (Anbu) signifies boundless love, kindness, and empathy toward all living beings."
      ),
      DiagnosticQuestion(
        id = "ta_q5",
        languageId = "ta",
        prompt = "What core philosophy is articulated in Kaniyan Pungundranar's Sangam verse: 'யாதும் ஊரே யாவரும் கேளிர்'?",
        scriptPrompt = "யாதும் ஊரே யாவரும் கேளிர்",
        options = listOf(
          "Victory in territorial battle brings eternal glory",
          "Every town is my native home, and all humanity are my kinsfolk",
          "Knowledge should only be passed down through hereditary lines",
          "Material wealth determines human virtue"
        ),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "This immortal Sangam poem from Purananuru reflects ancient Tamil universalism: all humanity shares one family bond."
      )
    ),

    "es" to listOf(
      DiagnosticQuestion(
        id = "es_q1",
        languageId = "es",
        prompt = "How do you say 'Good morning' in Spanish?",
        scriptPrompt = "Buenos días",
        options = listOf("Buenas noches", "Buenos días", "Hasta luego", "Mucho gusto"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'Buenos días' is the common greeting used throughout the morning hours until midday."
      ),
      DiagnosticQuestion(
        id = "es_q2",
        languageId = "es",
        prompt = "Which phrase politely asks for the bill at a restaurant?",
        scriptPrompt = "La cuenta, por favor",
        options = listOf("¿Dónde está el baño?", "¿Me trae la cuenta, por favor?", "¿Cuánto tiempo falta?", "¿Tiene una mesa?"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'¿Me trae la cuenta, por favor?' politely requests the bill from a server in dining settings."
      ),
      DiagnosticQuestion(
        id = "es_q3",
        languageId = "es",
        prompt = "Choose the correct preterite (past tense) verb form: 'Ayer nosotros _____ en el restaurante.'",
        scriptPrompt = "Verbo comer (Pretérito)",
        options = listOf("comemos", "comimos", "comeremos", "comían"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'Comimos' is the first-person plural preterite indicative form of the verb 'comer'."
      ),
      DiagnosticQuestion(
        id = "es_q4",
        languageId = "es",
        prompt = "What does the colloquial expression 'Estar en las nubes' convey?",
        scriptPrompt = "Estar en las nubes",
        options = listOf("To be flying on an airplane", "To be wealthy", "To be daydreaming or absent-minded", "To be very angry"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'Estar en las nubes' literally means 'to be in the clouds', indicating someone who is lost in thought or distracted."
      ),
      DiagnosticQuestion(
        id = "es_q5",
        languageId = "es",
        prompt = "Which sentence correctly demonstrates the subjunctive mood expressing a hope or wish?",
        scriptPrompt = "Modo subjuntivo",
        options = listOf(
          "Sé que vienes a la fiesta mañana.",
          "Ojalá que haga buen tiempo durante el viaje.",
          "Yo terminé todos mis deberes escolares.",
          "Ellos van al cine todos los domingos."
        ),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "'Ojalá que haga buen tiempo' utilizes the subjunctive verb 'haga' following the desire particle 'ojalá'."
      )
    ),

    "ja" to listOf(
      DiagnosticQuestion(
        id = "ja_q1",
        languageId = "ja",
        prompt = "What is the standard polite daytime greeting in Japanese?",
        scriptPrompt = "こんにちは",
        options = listOf("おはようございます (Ohayou)", "こんにちは (Konnichiwa)", "こんばんは (Konbanwa)", "さようなら (Sayounara)"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'こんにちは' (Konnichiwa) is the standard polite greeting from midday through late afternoon."
      ),
      DiagnosticQuestion(
        id = "ja_q2",
        languageId = "ja",
        prompt = "What gratitude phrase is customarily said before partaking in a meal?",
        scriptPrompt = "いただきます",
        options = listOf("ごちそうさまでした", "いただきます (Itadakimasu)", "いらっしゃいませ", "おじゃまします"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'いただきます' (Itadakimasu) humbly thanks all who prepared the meal and life given for nourishment."
      ),
      DiagnosticQuestion(
        id = "ja_q3",
        languageId = "ja",
        prompt = "Which honorific title is suitable when addressing a teacher, doctor, or master?",
        scriptPrompt = "敬称 (Honorifics)",
        options = listOf("〜さん (-san)", "〜くん (-kun)", "先生 (Sensei)", "〜ちゃん (-chan)"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'先生' (Sensei) conveys deep respect for educators, mentors, and medical professionals."
      ),
      DiagnosticQuestion(
        id = "ja_q4",
        languageId = "ja",
        prompt = "What does the multifaceted phrase 'すみません' (Sumimasen) mean depending on situation?",
        scriptPrompt = "すみません",
        options = listOf("Only 'Goodbye'", "Both 'Excuse me' and 'Thank you / Apologies for trouble'", "Only 'You are welcome'", "'See you tomorrow'"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'すみません' acts as an apology, polite attention getter, and courteous expression of gratitude for effort expended."
      ),
      DiagnosticQuestion(
        id = "ja_q5",
        languageId = "ja",
        prompt = "The aesthetic philosophy '物の哀れ' (Mono no aware) refers to:",
        scriptPrompt = "物の哀れ",
        options = listOf(
          "Unrelenting technical perfection in pottery",
          "Pathos and gentle melancholy regarding the transience and impermanence of beauty",
          "Strict obedience to corporate hierarchy",
          "Celebration of youthful invulnerability"
        ),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "'Mono no aware' captures profound aesthetic sensitivity toward the fleeting, impermanent nature of life and seasons."
      )
    ),

    "fr" to listOf(
      DiagnosticQuestion(
        id = "fr_q1",
        languageId = "fr",
        prompt = "How do you politely say 'Please' when speaking to someone formally in French?",
        scriptPrompt = "S'il vous plaît",
        options = listOf("De rien", "S'il vous plaît", "Merci beaucoup", "À bientôt"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'S'il vous plaît' uses the formal 'vous' form to politely request something."
      ),
      DiagnosticQuestion(
        id = "fr_q2",
        languageId = "fr",
        prompt = "When introduced to someone for the first time, which greeting expresses 'Pleased to meet you'?",
        scriptPrompt = "Enchanté",
        options = listOf("Au revoir", "Bonsoir", "Enchanté(e)", "Pardon"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'Enchanté' (or 'Enchantée') is the classic French courtesy phrase upon introduction."
      ),
      DiagnosticQuestion(
        id = "fr_q3",
        languageId = "fr",
        prompt = "Select the correct passé composé form: 'Hier soir, nous _____ une excellente pièce de théâtre.'",
        scriptPrompt = "Passé composé",
        options = listOf("avons vu", "voyons", "verrons", "avions voyé"),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'Avons vu' uses the auxiliary 'avoir' conjugated with 'nous' and the past participle of 'voir'."
      ),
      DiagnosticQuestion(
        id = "fr_q4",
        languageId = "fr",
        prompt = "What does the expression 'Ça ne fait rien' mean in conversation?",
        scriptPrompt = "Ça ne fait rien",
        options = listOf("It makes a lot of noise", "It doesn't matter / Don't worry about it", "It is very expensive", "It has started raining"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'Ça ne fait rien' is an idiomatic reassurance meaning 'never mind' or 'it is no trouble at all'."
      ),
      DiagnosticQuestion(
        id = "fr_q5",
        languageId = "fr",
        prompt = "The famous literary idiom 'Avoir le coup de foudre' literally translates to 'being struck by lightning', but figuratively means:",
        scriptPrompt = "Coup de foudre",
        options = listOf("Experiencing an electrical shock", "Suffering a sudden financial bankruptcy", "Falling in love at first sight", "Having a violent argument"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "'Coup de foudre' poetically describes the sudden, overwhelming sensation of instantaneous romantic attraction."
      )
    ),

    "de" to listOf(
      DiagnosticQuestion(
        id = "de_q1",
        languageId = "de",
        prompt = "What is the polite German greeting for 'Good day'?",
        scriptPrompt = "Guten Tag",
        options = listOf("Gute Nacht", "Guten Tag", "Auf Wiedersehen", "Tschüss"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'Guten Tag' is widely used across German-speaking countries as a formal and courteous daytime greeting."
      ),
      DiagnosticQuestion(
        id = "de_q2",
        languageId = "de",
        prompt = "Which word signifies 'Thank you very much' in German?",
        scriptPrompt = "Danke schön",
        options = listOf("Bitte schön", "Danke schön", "Entschuldigung", "Willkommen"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'Danke schön' or 'Vielen Dank' expresses sincere appreciation and thanks."
      ),
      DiagnosticQuestion(
        id = "de_q3",
        languageId = "de",
        prompt = "The compound noun 'Feierabend' represents what important cultural concept in Germany?",
        scriptPrompt = "Feierabend",
        options = listOf("A festive national holiday", "The end of the workday and start of relaxing personal evening", "A formal weekend dinner", "A religious ceremony"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'Feierabend' is the cherished boundary marking the conclusion of professional work and entry into leisure time."
      ),
      DiagnosticQuestion(
        id = "de_q4",
        languageId = "de",
        prompt = "Complete with the correct dative article: 'Ich helfe _____ alten Mann über die Straße.'",
        scriptPrompt = "Dativ-Objekt",
        options = listOf("der", "den", "dem", "des"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "The verb 'helfen' governs the dative case; masculine singular definite article in dative is 'dem'."
      ),
      DiagnosticQuestion(
        id = "de_q5",
        languageId = "de",
        prompt = "What does the distinct German cultural compound 'Schadenfreude' describe?",
        scriptPrompt = "Schadenfreude",
        options = listOf(
          "Joy experienced when helping others in need",
          "Secret pleasure or satisfaction derived from another person's mishap or misfortune",
          "Fear of missing an artistic performance",
          "Pride in academic craftsmanship"
        ),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "'Schadenfreude' combines 'Schaden' (damage) and 'Freude' (joy), famously denoting glee at another's predicament."
      )
    ),

    "zh" to listOf(
      DiagnosticQuestion(
        id = "zh_q1",
        languageId = "zh",
        prompt = "What is the formal and respectful greeting used for elders, teachers, and esteemed guests in Mandarin?",
        scriptPrompt = "您好",
        options = listOf("你好 (Nǐ hǎo)", "您好 (Nín hǎo)", "再见 (Zàijiàn)", "早安 (Zǎo'ān)"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'您好' (Nín hǎo) employs the honorific pronoun '您' (Nín) showing deference to respected individuals."
      ),
      DiagnosticQuestion(
        id = "zh_q2",
        languageId = "zh",
        prompt = "Which phrase means 'Thank you' in Mandarin Chinese?",
        scriptPrompt = "谢谢",
        options = listOf("不客气 (Bù kèqì)", "对不起 (Duìbùqǐ)", "谢谢 (Xièxie)", "没关系 (Méi guānxi)"),
        correctOptionIndex = 2,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "'谢谢' (Xièxie) is the standard and universal expression of gratitude."
      ),
      DiagnosticQuestion(
        id = "zh_q3",
        languageId = "zh",
        prompt = "How do you ask 'Where is the restroom / washroom?' in Mandarin?",
        scriptPrompt = "洗手间",
        options = listOf("洗手间在哪里？ (Xǐshǒujiān zài nǎlǐ?)", "请问几点了？ (Qǐngwèn jǐ diǎn le?)", "这多少钱？ (Zhè duōshao qián?)", "你叫什么名字？ (Nǐ jiào shénme míngzi?)"),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'洗手间在哪里？' directly inquires about the location of the washroom or restroom."
      ),
      DiagnosticQuestion(
        id = "zh_q4",
        languageId = "zh",
        prompt = "In Chinese grammar, what is the grammatical function of '了' (le) at the end of a verbal clause?",
        scriptPrompt = "语气助词 '了'",
        options = listOf("Indicates a question", "Marks completed action or change of state", "Negates the future tense", "Pluralizes nouns"),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "'了' (le) is an aspect particle indicating the completion of an action or an emergence of a new state."
      ),
      DiagnosticQuestion(
        id = "zh_q5",
        languageId = "zh",
        prompt = "What warning or philosophical moral is conveyed by the four-character Chengyu '半途而废' (Bàn tú ér fèi)?",
        scriptPrompt = "半途而废",
        options = listOf(
          "Always celebrate early before finishing",
          "Giving up halfway leads to total waste of prior effort",
          "Rushing headlong guarantees victory",
          "Working in isolation is superior to teamwork"
        ),
        correctOptionIndex = 1,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "'半途而废' warns against abandoning a worthy endeavor midway; perseverance to completion is essential."
      )
    )
  )

  fun getDiagnosticQuestionsForLanguage(language: Language): List<DiagnosticQuestion> {
    val specific = curatedQuestions[language.id]
    if (specific != null) return specific

    // Dynamic tailored question set for any cataloged language
    return listOf(
      DiagnosticQuestion(
        id = "${language.id}_q1",
        languageId = language.id,
        prompt = "How is the polite formal greeting expressed in ${language.name}?",
        scriptPrompt = language.formalGreeting,
        options = listOf(
          language.formalGreeting,
          "Goodbye and safe travels",
          "No thank you",
          "Excuse me, please"
        ),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "${language.formalGreeting} is the customary formal greeting in ${language.name}: '${language.greetingTranslation}'."
      ),
      DiagnosticQuestion(
        id = "${language.id}_q2",
        languageId = language.id,
        prompt = "Which script or writing system is traditionally utilized to write ${language.name}?",
        scriptPrompt = language.scriptName,
        options = listOf(
          language.scriptName,
          "Latin Script Only",
          "Egyptian Hieroglyphs",
          "Phoenician Cuneiform"
        ),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.BEGINNER,
        explanation = "${language.name} is predominantly written using ${language.scriptName}."
      ),
      DiagnosticQuestion(
        id = "${language.id}_q3",
        languageId = language.id,
        prompt = "What is an essential social principle in ${language.name} speaking communities?",
        scriptPrompt = language.culturalHighlight,
        options = listOf(
          language.culturalHighlight,
          "Strict silence during dining and celebrations",
          "Total avoidance of formal greetings",
          "Casual slang is required in all professional settings"
        ),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "A key cultural highlight: ${language.culturalHighlight}"
      ),
      DiagnosticQuestion(
        id = "${language.id}_q4",
        languageId = language.id,
        prompt = "In conversational ${language.name}, what distinguishes polite honorific discourse from casual speech?",
        scriptPrompt = "Polite vs Colloquial",
        options = listOf(
          "Use of formal pronouns, courteous verb suffixes, and respectful intonation",
          "Speaking louder and interrupting others",
          "Eliminating all verbs from the sentence",
          "There is no difference between formal and casual speech"
        ),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.INTERMEDIATE,
        explanation = "Polite register in ${language.name} utilizes respectful grammatical markers, formal pronouns, and attentive tone."
      ),
      DiagnosticQuestion(
        id = "${language.id}_q5",
        languageId = language.id,
        prompt = "Consider the celebrated proverb or motto in ${language.name}: '${language.mottoOrProverb}'. What core wisdom does it embody?",
        scriptPrompt = language.mottoOrProverb,
        options = listOf(
          "Timeless wisdom emphasizing perseverance, human unity, and continual lifelong learning",
          "Material accumulation is the highest virtue",
          "Only immediate physical strength matters",
          "Avoid traveling or learning new perspectives"
        ),
        correctOptionIndex = 0,
        targetLevel = ProficiencyLevel.ADVANCED,
        explanation = "This traditional proverb encapsulates the profound worldview, resilience, and philosophical heritage of ${language.name} speakers."
      )
    )
  }
}
