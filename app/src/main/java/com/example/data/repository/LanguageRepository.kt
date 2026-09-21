package com.example.data.repository

import com.example.data.model.CulturalCategory
import com.example.data.model.CulturalInsightItem
import com.example.data.model.Language
import com.example.data.model.SpeakingExercise
import com.example.data.model.WorldRegion

object LanguageRepository {

  val worldLanguages: List<Language> = listOf(
    Language(
      id = "ta",
      name = "Tamil",
      nativeName = "தமிழ்",
      flagEmoji = "🇮🇳",
      scriptName = "Tamil Script",
      region = WorldRegion.ASIA_INDIA.displayName,
      approximateSpeakers = "85 Million",
      formalGreeting = "வணக்கம் (Vanakkam)",
      greetingTranslation = "I offer you my deep respect and greetings",
      ttsLocaleCode = "ta-IN",
      culturalHighlight = "Ancient classical language dating back over 2,500 years; native heritage of Ramco Institute of Technology in Rajapalayam, Tamil Nadu.",
      mottoOrProverb = "யாதும் ஊரே யாவரும் கேளிர் (Every city is my town, all humanity are my kin)"
    ),
    Language(
      id = "es",
      name = "Spanish",
      nativeName = "Español",
      flagEmoji = "🇪🇸",
      scriptName = "Latin Alphabet",
      region = WorldRegion.AMERICAS.displayName,
      approximateSpeakers = "550 Million",
      formalGreeting = "¡Hola! Buenos días",
      greetingTranslation = "Hello! Good morning",
      ttsLocaleCode = "es-ES",
      culturalHighlight = "World's second most spoken native language with vibrant Hispanic traditions and warm conversational rhythm.",
      mottoOrProverb = "El que lee mucho y anda mucho, ve mucho y sabe mucho"
    ),
    Language(
      id = "ja",
      name = "Japanese",
      nativeName = "日本語",
      flagEmoji = "🇯🇵",
      scriptName = "Kanji, Hiragana, Katakana",
      region = WorldRegion.ASIA_INDIA.displayName,
      approximateSpeakers = "125 Million",
      formalGreeting = "こんにちは (Konnichiwa)",
      greetingTranslation = "Good day / Hello",
      ttsLocaleCode = "ja-JP",
      culturalHighlight = "Harmonious balance of deep honorific traditions (Keigo), mindful craftsmanship, and seasonal celebrations.",
      mottoOrProverb = "一期一会 (Ichigo Ichie - Treasure every unrepeatable encounter)"
    ),
    Language(
      id = "fr",
      name = "French",
      nativeName = "Français",
      flagEmoji = "🇫🇷",
      scriptName = "Latin Alphabet",
      region = WorldRegion.EUROPE.displayName,
      approximateSpeakers = "300 Million",
      formalGreeting = "Bonjour, enchanté",
      greetingTranslation = "Good day, pleased to meet you",
      ttsLocaleCode = "fr-FR",
      culturalHighlight = "Language of international diplomacy, haute cuisine, literature, and art across five continents.",
      mottoOrProverb = "Vouloir, c'est pouvoir (Where there's a will, there's a way)"
    ),
    Language(
      id = "de",
      name = "German",
      nativeName = "Deutsch",
      flagEmoji = "🇩🇪",
      scriptName = "Latin Alphabet",
      region = WorldRegion.EUROPE.displayName,
      approximateSpeakers = "130 Million",
      formalGreeting = "Guten Tag, freut mich",
      greetingTranslation = "Good day, pleased to meet you",
      ttsLocaleCode = "de-DE",
      culturalHighlight = "Language of science, philosophy, and engineering precision; celebrated for rich communal folk festivals.",
      mottoOrProverb = "Übung macht den Meister (Practice makes the master)"
    ),
    Language(
      id = "zh",
      name = "Mandarin Chinese",
      nativeName = "中文",
      flagEmoji = "🇨🇳",
      scriptName = "Chinese Hanzi (Simplified/Traditional)",
      region = WorldRegion.ASIA_INDIA.displayName,
      approximateSpeakers = "1.1 Billion",
      formalGreeting = "您好 (Nín hǎo)",
      greetingTranslation = "Respectful greetings to you",
      ttsLocaleCode = "zh-CN",
      culturalHighlight = "Millennia of continuous written heritage, tonal phonetics, poetic Chengyu idioms, and festive family harmony.",
      mottoOrProverb = "千里之行，始于足下 (A journey of a thousand miles begins with a single step)"
    ),
    Language(
      id = "ar",
      name = "Arabic",
      nativeName = "العربية",
      flagEmoji = "🇸🇦",
      scriptName = "Arabic Script (RTL)",
      region = WorldRegion.AFRICA_MIDDLE_EAST.displayName,
      approximateSpeakers = "400 Million",
      formalGreeting = "السلام عليكم (As-salamu alaykum)",
      greetingTranslation = "Peace be upon you",
      ttsLocaleCode = "ar-SA",
      culturalHighlight = "Poetic language of profound hospitality, rich calligraphy, generous generosity, and historic scholarship.",
      mottoOrProverb = "اطلبوا العلم من المهد إلى اللحد (Seek knowledge from the cradle to the grave)"
    ),
    Language(
      id = "hi",
      name = "Hindi",
      nativeName = "हिन्दी",
      flagEmoji = "🇮🇳",
      scriptName = "Devanagari",
      region = WorldRegion.ASIA_INDIA.displayName,
      approximateSpeakers = "600 Million",
      formalGreeting = "नमस्ते (Namaste)",
      greetingTranslation = "I bow to the divine within you",
      ttsLocaleCode = "hi-IN",
      culturalHighlight = "Major Indo-Aryan language with timeless philosophies of Atithi Devo Bhava (the guest is God) and vibrant arts.",
      mottoOrProverb = "वसुधैव कुटुम्बकम् (The whole world is one family)"
    ),
    Language(
      id = "ko",
      name = "Korean",
      nativeName = "한국어",
      flagEmoji = "🇰🇷",
      scriptName = "Hangul Alphabet",
      region = WorldRegion.ASIA_INDIA.displayName,
      approximateSpeakers = "80 Million",
      formalGreeting = "안녕하세요 (Annyeonghaseyo)",
      greetingTranslation = "Are you in peace / Hello",
      ttsLocaleCode = "ko-KR",
      culturalHighlight = "Scientific Hangul script designed by King Sejong, nuanced respect speech levels, and vibrant global pop culture.",
      mottoOrProverb = "시작이 반이다 (Starting is half the journey)"
    ),
    Language(
      id = "it",
      name = "Italian",
      nativeName = "Italiano",
      flagEmoji = "🇮🇹",
      scriptName = "Latin Alphabet",
      region = WorldRegion.EUROPE.displayName,
      approximateSpeakers = "68 Million",
      formalGreeting = "Buongiorno, piacere di conoscerla",
      greetingTranslation = "Good morning, pleasure to meet you",
      ttsLocaleCode = "it-IT",
      culturalHighlight = "Heartland of Renaissance arts, opera, expressive gestures, and deep culinary heritage.",
      mottoOrProverb = "Chi trova un amico, trova un tesoro (He who finds a friend finds a treasure)"
    ),
    Language(
      id = "ru",
      name = "Russian",
      nativeName = "Русский",
      flagEmoji = "🇷🇺",
      scriptName = "Cyrillic Script",
      region = WorldRegion.EUROPE.displayName,
      approximateSpeakers = "250 Million",
      formalGreeting = "Здравствуйте (Zdravstvuyte)",
      greetingTranslation = "Wishing you good health / Hello",
      ttsLocaleCode = "ru-RU",
      culturalHighlight = "Rich literary giants (Tolstoy, Dostoevsky), ballet, warm kitchen conversations, and hospitality.",
      mottoOrProverb = "Век живи — век учись (Live a century, learn for a century)"
    ),
    Language(
      id = "pt",
      name = "Portuguese",
      nativeName = "Português",
      flagEmoji = "🇧🇷",
      scriptName = "Latin Alphabet",
      region = WorldRegion.AMERICAS.displayName,
      approximateSpeakers = "260 Million",
      formalGreeting = "Olá, tudo bem?",
      greetingTranslation = "Hello, is everything good?",
      ttsLocaleCode = "pt-BR",
      culturalHighlight = "Global Lusophone heritage bridging South America and Europe, famous for the concept of 'Saudade' and infectious rhythm.",
      mottoOrProverb = "Quem não arrisca, não petisca (He who does not risk, does not snack/win)"
    ),
    Language(
      id = "sw",
      name = "Swahili",
      nativeName = "Kiswahili",
      flagEmoji = "🇰🇪",
      scriptName = "Latin Alphabet",
      region = WorldRegion.AFRICA_MIDDLE_EAST.displayName,
      approximateSpeakers = "150 Million",
      formalGreeting = "Hujambo / Shikamoo",
      greetingTranslation = "Greetings of high respect",
      ttsLocaleCode = "sw-KE",
      culturalHighlight = "Lingua franca of East Africa, rooted in the humanistic philosophy of Ubuntu ('I am because we are') and Harambee unity.",
      mottoOrProverb = "Haba na haba, hujaza kibaba (Little by little fills the measure)"
    )
  )

  // Dynamic Cultural Insight Modules for each language covering:
  // 1. Greetings & Hierarchy
  // 2. Dining Etiquette & Gastronomy
  // 3. Common Idioms & Wisdom
  // 4. Significant Holidays & Celebrations
  // 5. Taboos & Daily Customs
  val culturalInsights: List<CulturalInsightItem> = listOf(
    // ----------------- TAMIL (Home Heritage of RIT Autonomous) -----------------
    CulturalInsightItem(
      id = "ta_greet_1",
      languageId = "ta",
      category = CulturalCategory.GREETINGS,
      title = "Vanakkam with Folded Palms (அஞ்சலி முத்திரை)",
      originalPhrase = "வணக்கம் (Vanakkam)",
      phonetic = "Va-nak-kam",
      translation = "Respectful greeting uniting body and soul",
      culturalContext = "In Tamil Nadu and scholarly institutions like Ramco Institute of Technology, bringing palms together at chest level conveys that you honor the divinity, wisdom, and dignity in the other person.",
      etiquetteDo = "Gently bow your head slightly while saying Vanakkam when meeting professors, guests, or elders.",
      etiquetteDont = "Never offer a casual wave or slap on the back to seniors or faculty members.",
      significance = "Dates back to ancient Sangam literature; signifies humility and mutual moral respect.",
      iconEmoji = "🙏"
    ),
    CulturalInsightItem(
      id = "ta_dining_1",
      languageId = "ta",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "Traditional Banana Leaf Feast (வாழை இலை விருந்து)",
      originalPhrase = "வாழை இலை சாப்பாடு (Vaazhai Ilai Saappadu)",
      phonetic = "Vaa-zhai Ee-lai Saap-paa-du",
      translation = "Banana leaf dining and etiquette",
      culturalContext = "The top tapering end of the leaf is placed toward the guest's left. Salt, pickles, and payasam go on top, while rice is served in the center. Eating is traditionally done with the right hand fingers.",
      etiquetteDo = "After finishing a joyful meal, fold the leaf inward towards yourself (top to bottom) to show satisfaction to the host.",
      etiquetteDont = "Never fold the leaf away from you towards the front (which is traditionally reserved only for mourning occasions). Never use your left hand to touch the food.",
      significance = "Banana leaves contain natural polyphenols that enhance digestion and represent eco-friendly Tamil sustainability.",
      iconEmoji = "🍃"
    ),
    CulturalInsightItem(
      id = "ta_idiom_1",
      languageId = "ta",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "Drawing on a Wall (சுவர் இருந்தால் தான் சித்திரம்)",
      originalPhrase = "சுவர் இருந்தால் தான் சித்திரம் வரைய முடியும்",
      phonetic = "Suvar irundhaal thaan chithiram varaiya mudiyum",
      translation = "Only if there is a wall can you paint a picture",
      culturalContext = "A timeless Tamil proverb emphasizing that physical health and fundamental foundation must exist before one can pursue great artistic or intellectual achievements.",
      etiquetteDo = "Use this when reminding peers or students to prioritize their well-being and fundamental principles during intensive study.",
      etiquetteDont = "Do not take it literally as advice about masonry or interior wall painting!",
      significance = "Highlights the pragmatic, grounded philosophy of Tamil ethical thought.",
      iconEmoji = "🎨"
    ),
    CulturalInsightItem(
      id = "ta_holiday_1",
      languageId = "ta",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Thai Pongal & Tamizhar Thirunal (தைப்பொங்கல்)",
      originalPhrase = "பொங்கலோ பொங்கல்! (Pongalo Pongal!)",
      phonetic = "Pon-ga-lo Pon-gal!",
      translation = "May abundance and prosperity overflow!",
      culturalContext = "Celebrated in mid-January, Thai Pongal is the four-day harvest thanksgiving festival of Tamil Nadu. Freshly harvested rice is boiled in earthen pots with milk and jaggery until it spills over, symbolizing wealth and fortune.",
      etiquetteDo = "Greet others with 'இனிய தைப்பொங்கல் நல்வாழ்த்துகள்' (Iniya Thai Pongal Nalvazhthukkal) and share sweet Sakkarai Pongal.",
      etiquetteDont = "Avoid scheduling strenuous exams or solemn business meetings on Boghi or Surya Pongal day.",
      significance = "Expresses gratitude to the Sun God (Surya) and cattle (Mattu Pongal) for supporting human agriculture.",
      iconEmoji = "🌾"
    ),

    // ----------------- SPANISH -----------------
    CulturalInsightItem(
      id = "es_greet_1",
      languageId = "es",
      category = CulturalCategory.GREETINGS,
      title = "Dos Besos & Formal vs Informal (Tú vs Usted)",
      originalPhrase = "¿Cómo está usted? / ¡Hola!",
      phonetic = "Ko-mo es-ta oos-ted",
      translation = "How are you (formal) / Hello",
      culturalContext = "In Spain, social greetings involve two light cheek air-kisses (starting right cheek). In Latin America, a warm single cheek kiss or handshake is standard. Addressing someone with 'Usted' shows deference to age and rank.",
      etiquetteDo = "Use 'Usted' until the other person warmly suggests: 'Puedes tutearme' (You can address me informally).",
      etiquetteDont = "Don't plant wet lips directly on the cheek; it is a gentle cheek-to-cheek touch with an air kiss sound.",
      significance = "Reflects the warm relational warmth and sociability (convivencia) of Hispanic cultures.",
      iconEmoji = "👋"
    ),
    CulturalInsightItem(
      id = "es_dining_1",
      languageId = "es",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "La Sobremesa (Conversations after Dining)",
      originalPhrase = "La Sobremesa",
      phonetic = "La so-bre-me-sa",
      translation = "Lingering over the table after meal",
      culturalContext = "Meals in Spanish-speaking worlds are leisurely social ceremonies. Guests never rush off immediately after the last bite. Instead, people remain at the table for an hour or more conversing over coffee.",
      etiquetteDo = "Keep both hands visible on top of the table (wrists resting on the edge, not elbows). Participate eagerly in table discussions.",
      etiquetteDont = "Never ask for the bill immediately or make signs of wanting to leave the moment food is finished.",
      significance = "Values human relationship and unhurried companionship over fast food efficiency.",
      iconEmoji = "☕"
    ),
    CulturalInsightItem(
      id = "es_idiom_1",
      languageId = "es",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "Costar un Ojo de la Cara (Costing an eye)",
      originalPhrase = "Costar un ojo de la cara",
      phonetic = "Kos-tar oon o-ho de la ka-ra",
      translation = "To cost an eye of the face (Extremely expensive)",
      culturalContext = "Equivalent to 'costs an arm and a leg' in English. Originates from conquistador Diego de Almagro who literally lost an eye in battle.",
      etiquetteDo = "Use casually with friends when discussing extravagant prices of designer items or luxury vacations.",
      etiquetteDont = "Avoid using it in formal corporate negotiations as it is colloquial.",
      significance = "Vivid anatomical imagery typical of Spanish everyday figurative expressions.",
      iconEmoji = "👁️"
    ),
    CulturalInsightItem(
      id = "es_holiday_1",
      languageId = "es",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Día de los Muertos (Day of the Dead)",
      originalPhrase = "¡Feliz Día de los Muertos!",
      phonetic = "Fe-leez Dee-ah de los Mwer-tos",
      translation = "Honoring the journey of departed souls",
      culturalContext = "Celebrated on Nov 1-2 across Mexico and Latin America. Rather than mourning, it is a joyous reunion with departed ancestors using vibrant marigold flowers (cempasúchil), sugar skulls, and home altars (ofrendas).",
      etiquetteDo = "Admire ofrendas with respectful curiosity; understand that death is embraced as a natural chapter of existence.",
      etiquetteDont = "Never treat it as a Halloween costume party or mock the solemnity of personal family photos on altars.",
      significance = "Recognized by UNESCO as Intangible Cultural Heritage of Humanity.",
      iconEmoji = "💀"
    ),

    // ----------------- JAPANESE -----------------
    CulturalInsightItem(
      id = "ja_greet_1",
      languageId = "ja",
      category = CulturalCategory.GREETINGS,
      title = "Bowing Etiquette & Angles (お辞儀 - Ojigi)",
      originalPhrase = "失礼いたします (Shitsurei itashimasu)",
      phonetic = "Shit-tsu-rey ee-ta-she-ma-soo",
      translation = "Excuse me / I am being impolite",
      culturalContext = "Japanese bowing communicates degree of respect: 15° for casual greeting (Eshaku), 30° for business respect (Keirei), and 45° for deep gratitude or solemn apology (Saikeirei).",
      etiquetteDo = "Keep your spine straight, eyes facing downward in line with your neck, and pause at the bottom of the bow for one breath.",
      etiquetteDont = "Do not maintain awkward direct eye contact while bowing, and never bow while walking.",
      significance = "Reflects the foundational concept of 'Wa' (harmony) and social mindfulness.",
      iconEmoji = "🙇"
    ),
    CulturalInsightItem(
      id = "ja_dining_1",
      languageId = "ja",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "Chopstick Taboos & Slurping Ramen",
      originalPhrase = "いただきます (Itadakimasu)",
      phonetic = "Ee-ta-da-ki-mas",
      translation = "I humbly receive this sustenance",
      culturalContext = "Slurping hot soba or ramen noodles cools them and aerates the broth, which is considered a compliment to the chef! However, placing chopsticks vertically upright in rice (Tsukitate-bashi) is strictly taboo as it mirrors funeral rites.",
      etiquetteDo = "Say 'Itadakimasu' before eating and 'Gochisousama deshita' when finished.",
      etiquetteDont = "Never pass food chopstick-to-chopstick (Hashi-watashi), which also mimics bone collection rites.",
      significance = "Gratitude for nature and meticulous mindfulness at every meal.",
      iconEmoji = "🍜"
    ),
    CulturalInsightItem(
      id = "ja_idiom_1",
      languageId = "ja",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "Wanting to Borrow a Cat's Paws (猫の手も借りたい)",
      originalPhrase = "猫の手も借りたい (Neko no te mo karitai)",
      phonetic = "Ne-ko no te mo ka-ri-tai",
      translation = "I would even borrow a cat's paws",
      culturalContext = "Expresses being overwhelmingly busy to the point where even a completely useless assistant (like a lazy cat's paw) would be welcomed for help!",
      etiquetteDo = "Use self-deprecatingly when swamped with work or project deadlines.",
      etiquetteDont = "Don't say it to an actual boss as an excuse to avoid a task.",
      significance = "Highlights Japanese affection for endearing animal metaphors in daily speech.",
      iconEmoji = "🐾"
    ),
    CulturalInsightItem(
      id = "ja_holiday_1",
      languageId = "ja",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Hanami - Cherry Blossom Viewing (花見)",
      originalPhrase = "お花見 (O-Hanami)",
      phonetic = "O-ha-na-mi",
      translation = "Contemplating transient floral beauty",
      culturalContext = "In spring, people gather under blooming Sakura trees with family, friends, and colleagues with bento boxes and drinks, celebrating both beauty and the bittersweet transience of life (Mono no aware).",
      etiquetteDo = "Always carry all your trash home or use designated bins; leave the park immaculate.",
      etiquetteDont = "Never break, shake, or climb cherry blossom branches to take photos.",
      significance = "Teaches that true beauty is precious precisely because it does not last forever.",
      iconEmoji = "🌸"
    ),

    // ----------------- FRENCH -----------------
    CulturalInsightItem(
      id = "fr_greet_1",
      languageId = "fr",
      category = CulturalCategory.GREETINGS,
      title = "The Sacred 'Bonjour' before Any Transaction",
      originalPhrase = "Bonjour Madame / Monsieur",
      phonetic = "Bon-zhoor mah-dahm / muh-syur",
      translation = "Good day Madam / Sir",
      culturalContext = "In France, entering any shop, café, or asking for directions without first saying 'Bonjour' is considered deeply rude. It acknowledges the other person's humanity before engaging in a transaction.",
      etiquetteDo = "Always initiate contact with a clear 'Bonjour' and smile before asking questions.",
      etiquetteDont = "Never start immediately with 'Where is the metro?' or 'How much is this?' without the greeting.",
      significance = "The bedrock of French civic politeness and equal human dignity.",
      iconEmoji = "🥐"
    ),
    CulturalInsightItem(
      id = "fr_dining_1",
      languageId = "fr",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "Bread directly on the Tablecloth (Le Pain)",
      originalPhrase = "Bon appétit!",
      phonetic = "Bon ah-pay-tee",
      translation = "Enjoy your meal",
      culturalContext = "In French restaurants and homes, bread is not an appetizer; it accompanies the entire meal. It is often placed directly on the tablecloth beside your plate rather than on a separate bread dish.",
      etiquetteDo = "Tear small bite-sized pieces of bread with your hands rather than biting directly into a whole roll.",
      etiquetteDont = "Never cut bread with your knife unless it is a baguette loaf being sliced for the table.",
      significance = "Bread is sacred in French culinary democracy and cultural identity.",
      iconEmoji = "🥖"
    ),
    CulturalInsightItem(
      id = "fr_idiom_1",
      languageId = "fr",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "Avoir le Cafard (Having the Cockroach)",
      originalPhrase = "Avoir le cafard",
      phonetic = "Ah-vwar luh kah-far",
      translation = "To have the cockroach (Feeling melancholic / down)",
      culturalContext = "Popularized by poet Charles Baudelaire in 'Les Fleurs du Mal'. It poetically describes feeling melancholic, blue, or listless without harsh clinical terms.",
      etiquetteDo = "Use when softly confiding in close friends that you're feeling down: 'J'ai un peu le cafard aujourd'hui'.",
      etiquetteDont = "Don't use in official medical records or job interviews.",
      significance = "Exemplifies the introspective poetic romanticism of the French language.",
      iconEmoji = "🪲"
    ),
    CulturalInsightItem(
      id = "fr_holiday_1",
      languageId = "fr",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "La Fête Nationale / Bastille Day (14 Juillet)",
      originalPhrase = "Bonne Fête Nationale!",
      phonetic = "Bonn fet nah-syoh-nahl",
      translation = "Happy National Holiday!",
      culturalContext = "Commemorates the storming of the Bastille in 1789 and the Fête de la Fédération, marking the triumph of the Republic and 'Liberté, Égalité, Fraternité'. Features iconic fireworks by the Eiffel Tower and firemen's balls (Bals des pompiers).",
      etiquetteDo = "Join the public open-air dances in city squares with locals.",
      etiquetteDont = "Avoid calling it 'Bastille Day' in France; locals call it simply 'Le Quatorze Juillet'.",
      significance = "Celebrates the democratic equality of citizens and civic unity.",
      iconEmoji = "🎆"
    ),

    // ----------------- GERMAN -----------------
    CulturalInsightItem(
      id = "de_greet_1",
      languageId = "de",
      category = CulturalCategory.GREETINGS,
      title = "Eye Contact during Toasts & Firm Handshakes",
      originalPhrase = "Prost! / Zum Wohl!",
      phonetic = "Prohst / Tsoom Vohl",
      translation = "Cheers! / To your well-being!",
      culturalContext = "When toasting in Germany, you must make direct eye contact with each person as glasses touch. Failing to maintain eye contact is playfully warned to bring bad luck!",
      etiquetteDo = "Look each companion in the eye as you clink glasses and say 'Prost!'. Provide a short, firm handshake when introduced.",
      etiquetteDont = "Never cross arms over someone else's glass while clinking.",
      significance = "Directness, sincerity, and presence in the shared moment.",
      iconEmoji = "🍻"
    ),
    CulturalInsightItem(
      id = "de_dining_1",
      languageId = "de",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "Quiet Sundays & Knife-and-Fork Etiquette",
      originalPhrase = "Guten Appetit!",
      phonetic = "Goo-ten ah-peh-teet",
      translation = "Enjoy your appetite",
      culturalContext = "Germans use continental dining: fork remains in left hand, knife in right throughout the meal. When finished, place knife and fork parallel across the plate at the 4:20 clock angle.",
      etiquetteDo = "Signal to the waiter you have finished by resting utensils neatly together on the plate.",
      etiquetteDont = "Don't cut potatoes with a knife if soft; gently crush them with the side of a fork to soak up gravy, showing the chef they were cooked to tender perfection.",
      significance = "Practical efficiency paired with deep culinary appreciation.",
      iconEmoji = "🍽️"
    ),
    CulturalInsightItem(
      id = "de_idiom_1",
      languageId = "de",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "I Only Understand Train Station (Ich verstehe nur Bahnhof)",
      originalPhrase = "Ich verstehe nur Bahnhof",
      phonetic = "Ikh fer-shtay-uh noor Bahn-hof",
      translation = "I only understand train station (It's all Greek to me)",
      culturalContext = "Originated during WWI when exhausted soldiers only wanted to hear about the train station that would take them back home to their families.",
      etiquetteDo = "Use cheerfully when an explanation is too technical or complex to grasp.",
      etiquetteDont = "Don't use to dismiss someone who is giving serious safety instructions.",
      significance = "Humorous German self-honesty when clarity is missing.",
      iconEmoji = "🚂"
    ),
    CulturalInsightItem(
      id = "de_holiday_1",
      languageId = "de",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Oktoberfest & Tag der Deutschen Einheit",
      originalPhrase = "O'zapft is! (It is tapped!)",
      phonetic = "Oh-tsahpft ees",
      translation = "The barrel is tapped!",
      culturalContext = "Originating in Munich in 1810 to celebrate a royal wedding, Oktoberfest has grown into the world's largest folk festival. People wear traditional Tracht (Lederhosen and Dirndl), singing folk anthems in communal beer tents.",
      etiquetteDo = "Sing along with tent bands and share long wooden tables with strangers from all over the world.",
      etiquetteDont = "Never attempt to take official Oktoberfest beer steins (Maßkrüge) as souvenirs; security will confiscate them.",
      significance = "Celebrates German regional pride, music, and brotherhood.",
      iconEmoji = "🥨"
    ),

    // ----------------- MANDARIN CHINESE -----------------
    CulturalInsightItem(
      id = "zh_greet_1",
      languageId = "zh",
      category = CulturalCategory.GREETINGS,
      title = "Two-Handed Presentation & 'Have You Eaten?'",
      originalPhrase = "你吃了吗？ (Nǐ chīle ma?)",
      phonetic = "Nee chur luh mah?",
      translation = "Have you eaten yet?",
      culturalContext = "In Chinese culture, asking if someone has eaten is the warmest, most traditional greeting—equivalent to 'How are you?'. It shows care for their fundamental comfort.",
      etiquetteDo = "Always hand over business cards, gifts, or tea with BOTH hands, facing the text towards the receiver.",
      etiquetteDont = "Don't answer 'No, I haven't' expecting an immediate feast—a simple 'Chīle, nǐ ne?' (I have, and you?) is polite.",
      significance = "Historically rooted in agrarian care where food was the measure of security and wellness.",
      iconEmoji = "🪪"
    ),
    CulturalInsightItem(
      id = "zh_dining_1",
      languageId = "zh",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "The Lazy Susan & Finger Tapping for Tea",
      originalPhrase = "慢慢吃 (Màn màn chī)",
      phonetic = "Mahn mahn chur",
      translation = "Eat slowly and enjoy",
      culturalContext = "Dishes sit on a rotating turntable for communal sharing. When someone pours tea into your cup, tap your bent index and middle fingers twice on the table to express silent thanks without interrupting conversation.",
      etiquetteDo = "Always turn the turntable clockwise and ensure elders or guests take from a fresh dish first.",
      etiquetteDont = "Never turn the turntable while someone else is actively reaching for food.",
      significance = "Originates from the Qing dynasty emperor who traveled incognito and thanked servants with finger taps.",
      iconEmoji = "🫖"
    ),
    CulturalInsightItem(
      id = "zh_idiom_1",
      languageId = "zh",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "Blessing in Disguise (塞翁失马，焉知非福)",
      originalPhrase = "塞翁失马 (Sài wēng shī mǎ)",
      phonetic = "Sai weng shrr mah",
      translation = "Old man at the frontier lost his horse",
      culturalContext = "From a classic Daoist parable where an old man loses his horse (seeming misfortune), which later returns with wild stallions (fortune), leading his son to ride and break his leg (misfortune), which saves him from war conscription (fortune).",
      etiquetteDo = "Quote this four-character Chengyu when comforting someone who experienced a minor setback.",
      etiquetteDont = "Don't use it lightly in times of profound tragedy.",
      significance = "Teaches emotional resilience and the cyclical nature of yin and yang.",
      iconEmoji = "🐎"
    ),
    CulturalInsightItem(
      id = "zh_holiday_1",
      languageId = "zh",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Spring Festival / Lunar New Year (春节)",
      originalPhrase = "新年快乐，万事如意！",
      phonetic = "Xīn-nián kuài-lè, wàn-shì rú-yì!",
      translation = "Happy New Year, may all your wishes come true!",
      culturalContext = "The most important holiday in the Chinese calendar. Millions travel home (Chunyun) for family reunion dinners, eating dumplings (Jiaozi) shaped like gold ingots and giving red envelopes (Hongbao) containing lucky money.",
      etiquetteDo = "Wear bright or red clothing to bring luck and ward off negative energy.",
      etiquetteDont = "Never sweep the floor or wash your hair on the first day of the New Year, as it symbolically washes away good luck.",
      significance = "Renewal, filial family bonds, and optimistic hopes for prosperity.",
      iconEmoji = "🧧"
    ),

    // ----------------- ARABIC -----------------
    CulturalInsightItem(
      id = "ar_greet_1",
      languageId = "ar",
      category = CulturalCategory.GREETINGS,
      title = "Hand over Heart & The Sacred Salam",
      originalPhrase = "السلام عليكم ورحمة الله (As-salamu alaykum)",
      phonetic = "As-sah-lah-moo ah-lay-koom",
      translation = "May peace and mercy be upon you",
      culturalContext = "The universally honored greeting across the Arab world. Placing the right hand gently over the heart after greeting or shaking hands signifies deep sincerity and friendship.",
      etiquetteDo = "Reply with 'Wa alaykum as-salam' (And upon you be peace). Wait for women to initiate handshakes if applicable.",
      etiquetteDont = "Never offer your left hand for a handshake, greeting, or passing objects.",
      significance = "Conveys peace as a moral covenant between two human beings.",
      iconEmoji = "🕊️"
    ),
    CulturalInsightItem(
      id = "ar_dining_1",
      languageId = "ar",
      category = CulturalCategory.DINING_ETIQUETTE,
      title = "Cardamom Arabic Coffee & Shaking the Finjan Cup",
      originalPhrase = "قهوة عربية (Qahwah Arabiyya)",
      phonetic = "Qah-wah Ah-rah-bee-yah",
      translation = "Spiced Arabic coffee with dates",
      culturalContext = "Serving freshly brewed golden cardamom coffee in small handle-less cups (finjan) is a legendary symbol of generosity. When your cup is full, take it with your right hand. When you do not want more coffee, gently tilt and shake the cup from side to side.",
      etiquetteDo = "Accept at least the first cup of coffee as refusing can unintentionally offend the host.",
      etiquetteDont = "Never use your left hand to hold the coffee cup or dates.",
      significance = "Hospitality (Karam) is the highest social virtue in Arabian heritage.",
      iconEmoji = "☕"
    ),
    CulturalInsightItem(
      id = "ar_idiom_1",
      languageId = "ar",
      category = CulturalCategory.COMMON_IDIOMS,
      title = "On My Head (على راسي - Ala Rasi)",
      originalPhrase = "على راسي (Ala rasi)",
      phonetic = "Ah-lah rah-see",
      translation = "Upon my head (With the greatest pleasure / At your service)",
      culturalContext = "When someone asks for a favor or request, replying 'Ala rasi' means you place their request with the utmost honor upon your own head. It is the peak of chivalrous courtesy.",
      etiquetteDo = "Use when gladly helping a friend, family member, or respectful guest.",
      etiquetteDont = "Don't use it cynically or sarcastically.",
      significance = "Highlights the noble, poetic dedication to serving others.",
      iconEmoji = "👑"
    ),
    CulturalInsightItem(
      id = "ar_holiday_1",
      languageId = "ar",
      category = CulturalCategory.HOLIDAYS_FESTIVALS,
      title = "Eid al-Fitr (عيد الفطر) & Ramadan",
      originalPhrase = "عيد مبارك وكل عام وأنتم بخير",
      phonetic = "Eid Mubarak wa kullu aam wa antum bi-khayr",
      translation = "Blessed Eid, may you be in wellness every year",
      culturalContext = "Marks the conclusion of Ramadan, the holy month of fasting, reflection, and community charity (Zakat). Families dress in their finest clothes, exchange gifts, visit relatives, and share traditional date pastries (Ma'amoul).",
      etiquetteDo = "Greet friends and colleagues with 'Eid Mubarak!' and share sweets generously.",
      etiquetteDont = "Do not eat publicly in broad daylight during fasting hours in Ramadan out of respect for those observing.",
      significance = "Spiritual rejuvenation, compassion for the less fortunate, and joyous family bonds.",
      iconEmoji = "🌙"
    )
  )

  // Practical speaking exercises across world languages
  val speakingExercises: List<SpeakingExercise> = listOf(
    SpeakingExercise(
      id = "ta_spk_1",
      languageId = "ta",
      scenario = "Formal Academic & Campus Greeting",
      level = "Beginner to Intermediate",
      phrase = "வணக்கம் ஐயா, நான் ராம்கோ தொழில்நுட்பக் கல்லூரியில் படிக்கிறேன்.",
      phonetic = "Vanakkam aiyaa, naan Ramco thozhilnutpa kalloori-yil padikkiren.",
      englishTranslation = "Greetings Sir, I study at Ramco Institute of Technology.",
      culturalUsageTip = "Add 'ஐயா' (Aiyaa) or 'அம்மா' (Amma) when speaking politely to senior professors or dignitaries.",
      audioLocaleTag = "ta-IN"
    ),
    SpeakingExercise(
      id = "ta_spk_2",
      languageId = "ta",
      scenario = "Cultural Hospitality & Tea",
      level = "Conversational",
      phrase = "வாருங்கள், நலம் தானா? ஒரு கப் சுடச்சுட தேநீர் அருந்துங்கள்.",
      phonetic = "Vaarungal, nalam thaanaa? Oru cup suda-suda theneer arundhungal.",
      englishTranslation = "Welcome, are you doing well? Please enjoy a cup of piping hot tea.",
      culturalUsageTip = "Offering tea is the gold standard of Tamil hospitality when welcoming a visitor.",
      audioLocaleTag = "ta-IN"
    ),
    SpeakingExercise(
      id = "es_spk_1",
      languageId = "es",
      scenario = "Ordering at a Café in Madrid",
      level = "Beginner",
      phrase = "Buenos días, ¿me pone un café con leche y una tostada, por favor?",
      phonetic = "Bweh-nos dee-as, meh poh-neh oon kah-feh kohn leh-cheh ee oo-nah tohs-tah-dah poor fah-voor?",
      englishTranslation = "Good morning, could you serve me a coffee with milk and toast, please?",
      culturalUsageTip = "'¿Me pone...?' is the natural polite idiom used across Spanish cafés rather than literal translation 'I want'.",
      audioLocaleTag = "es-ES"
    ),
    SpeakingExercise(
      id = "es_spk_2",
      languageId = "es",
      scenario = "Expressing Deep Gratitude",
      level = "Polite Formal",
      phrase = "Muchas gracias por su hospitalidad, ha sido un verdadero placer.",
      phonetic = "Moo-chas grah-syas poor soo ohs-pee-tah-lee-dahd, ah see-doh oon vehr-dah-deh-roh plah-sehr.",
      englishTranslation = "Thank you very much for your hospitality, it has been a true pleasure.",
      culturalUsageTip = "Using 'su' (formal your) shows refined appreciation to your host.",
      audioLocaleTag = "es-ES"
    ),
    SpeakingExercise(
      id = "ja_spk_1",
      languageId = "ja",
      scenario = "Meeting for the First Time (Jikoshoukai)",
      level = "Intermediate Respectful",
      phrase = "初めまして。どうぞよろしくお願いいたします。",
      phonetic = "Hajimemashite. Douzo yoroshiku onegai itashimasu.",
      englishTranslation = "Pleased to meet you. Please treat me favorably.",
      culturalUsageTip = "Deliver this phrase with a gentle 30-degree bow. It is the cornerstone of all Japanese social relationships.",
      audioLocaleTag = "ja-JP"
    ),
    SpeakingExercise(
      id = "ja_spk_2",
      languageId = "ja",
      scenario = "Complimenting the Host's Food",
      level = "Beginner",
      phrase = "とても美味しいです！ごちそうさまでした。",
      phonetic = "Totemo oishii desu! Gochisousama deshita.",
      englishTranslation = "It is very delicious! Thank you for the wonderful feast.",
      culturalUsageTip = "Saying 'Gochisousama deshita' honors the effort the cook and farmers took to bring the food to you.",
      audioLocaleTag = "ja-JP"
    ),
    SpeakingExercise(
      id = "fr_spk_1",
      languageId = "fr",
      scenario = "Polite Bakery Purchase",
      level = "Beginner",
      phrase = "Bonjour Madame, une baguette tradition bien cuite, s'il vous plaît.",
      phonetic = "Bon-zhoor mah-dahm, oon bah-get trah-dee-syon byan kweet, seel voo pleh.",
      englishTranslation = "Good day Madam, a well-baked traditional baguette, please.",
      culturalUsageTip = "Always end with 's'il vous plaît' and greet before naming the item.",
      audioLocaleTag = "fr-FR"
    ),
    SpeakingExercise(
      id = "de_spk_1",
      languageId = "de",
      scenario = "Formal Introduction at a Conference",
      level = "Intermediate",
      phrase = "Guten Tag, es ist mir eine große Freude, Sie kennenzulernen.",
      phonetic = "Goo-ten Tahg, es ist meer eye-nuh groh-suh Froy-duh, Zee ken-nen-tsoo-lehr-nen.",
      englishTranslation = "Good day, it is a great pleasure to make your acquaintance.",
      culturalUsageTip = "Capitalized 'Sie' denotes respectful formal address for professional colleagues.",
      audioLocaleTag = "de-DE"
    ),
    SpeakingExercise(
      id = "zh_spk_1",
      languageId = "zh",
      scenario = "Visiting a Host & Expressing Honor",
      level = "Polite",
      phrase = "您太客气了，非常感谢您的热情招待！",
      phonetic = "Nín tài kè-qi le, fēi-cháng gǎn-xiè nín de rè-qíng zhāo-dài!",
      englishTranslation = "You are far too polite, thank you so much for your warm hospitality!",
      culturalUsageTip = "'太客气了' (tài kèqi le) modestly deflects excess formality while honoring the host's generosity.",
      audioLocaleTag = "zh-CN"
    ),
    SpeakingExercise(
      id = "ar_spk_1",
      languageId = "ar",
      scenario = "Greeting a Host & Praising Hospitality",
      level = "Respectful",
      phrase = "أهلاً وسهلاً، شرفتمونا بحضوركم الكريم.",
      phonetic = "Ahlan wa sahlan, sharraftoona bi-hudoorikum al-kareem.",
      englishTranslation = "Welcome, you have deeply honored us with your noble presence.",
      culturalUsageTip = "Arabs say 'Ahlan wa sahlan' (You have come to kin and an easy plane) to make visitors feel completely safe.",
      audioLocaleTag = "ar-SA"
    )
  )

  fun getLanguageById(id: String): Language {
    return worldLanguages.find { it.id == id } ?: worldLanguages.first()
  }

  fun getCulturalInsightsForLanguage(languageId: String): List<CulturalInsightItem> {
    val matches = culturalInsights.filter { it.languageId == languageId }
    if (matches.isNotEmpty()) return matches
    // Provide rich fallback insight items for languages with dynamic placeholders
    val lang = getLanguageById(languageId)
    return listOf(
      CulturalInsightItem(
        id = "${languageId}_gen_greet",
        languageId = languageId,
        category = CulturalCategory.GREETINGS,
        title = "Formal Greetings & Respectful Distance",
        originalPhrase = lang.formalGreeting,
        phonetic = lang.greetingTranslation,
        translation = "Standard cordial greeting",
        culturalContext = "In ${lang.name} culture, greeting someone with ${lang.formalGreeting} establishes warmth, mutual trust, and social harmony.",
        etiquetteDo = "Offer a pleasant smile and adjust physical posture according to age and seniority.",
        etiquetteDont = "Do not bypass introductory salutations before inquiring about practical matters.",
        significance = "Foundational pillar of ${lang.name} everyday diplomacy and mutual respect.",
        iconEmoji = "🤝"
      ),
      CulturalInsightItem(
        id = "${languageId}_gen_dining",
        languageId = languageId,
        category = CulturalCategory.DINING_ETIQUETTE,
        title = "Traditional Meal Etiquette & Toasting",
        originalPhrase = "Bon Appétit / Saúde",
        phonetic = "To your health and enjoyment",
        translation = "Enjoy your sustenance",
        culturalContext = "Dining among ${lang.name} speakers is a collective celebration of shared food, ancestry, and deep conversation.",
        etiquetteDo = "Wait for the host to initiate the toast or take the first bite before beginning.",
        etiquetteDont = "Avoid leaving abruptly when food is finished; partake in communal tea or conversation.",
        significance = "Demonstrates gratitude to both the chef and companionship.",
        iconEmoji = "🍽️"
      ),
      CulturalInsightItem(
        id = "${languageId}_gen_idiom",
        languageId = languageId,
        category = CulturalCategory.COMMON_IDIOMS,
        title = "Wisdom & Untranslatable Expressions",
        originalPhrase = lang.mottoOrProverb,
        phonetic = "Ancient proverb",
        translation = "Cultural wisdom passed down through generations",
        culturalContext = "Proverbs in ${lang.name} convey centuries of lived experience, philosophy, and practical advice.",
        etiquetteDo = "Use idioms thoughtfully in conversations to show high linguistic appreciation.",
        etiquetteDont = "Do not translate idioms word-for-word without understanding metaphorical context.",
        significance = "Preserves ancestral worldview in poetic cadence.",
        iconEmoji = "💬"
      ),
      CulturalInsightItem(
        id = "${languageId}_gen_holiday",
        languageId = languageId,
        category = CulturalCategory.HOLIDAYS_FESTIVALS,
        title = "Major Cultural Holidays & Community Joy",
        originalPhrase = "Seasonal Celebration",
        phonetic = "Festive season",
        translation = "Annual holiday of unity and celebration",
        culturalContext = "Holidays bring ${lang.name} families and communities together through special culinary dishes, folk music, and time-honored rituals.",
        etiquetteDo = "Send celebratory greetings in the local language during festive periods.",
        etiquetteDont = "Do not disregard cultural holidays when planning international collaborations.",
        significance = "Strengthens intergenerational solidarity and identity.",
        iconEmoji = "🎉"
      )
    )
  }

  fun getSpeakingExercisesForLanguage(languageId: String): List<SpeakingExercise> {
    val matches = speakingExercises.filter { it.languageId == languageId }
    if (matches.isNotEmpty()) return matches
    val lang = getLanguageById(languageId)
    return listOf(
      SpeakingExercise(
        id = "${languageId}_def_1",
        languageId = languageId,
        scenario = "Everyday Cordial Greeting",
        level = "Beginner",
        phrase = lang.formalGreeting,
        phonetic = lang.greetingTranslation,
        englishTranslation = lang.greetingTranslation,
        culturalUsageTip = "Speak clearly with warm tone and respectful eye contact.",
        audioLocaleTag = lang.ttsLocaleCode
      ),
      SpeakingExercise(
        id = "${languageId}_def_2",
        languageId = languageId,
        scenario = "Proverb & Cultural Wisdom",
        level = "Intermediate",
        phrase = lang.mottoOrProverb,
        phonetic = "Philosophical proverb",
        englishTranslation = "Heritage wisdom of ${lang.name}",
        culturalUsageTip = "Focus on smooth phrasing and natural pauses between clauses.",
        audioLocaleTag = lang.ttsLocaleCode
      )
    )
  }
}
