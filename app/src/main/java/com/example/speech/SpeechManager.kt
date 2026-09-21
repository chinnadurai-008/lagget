package com.example.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class SpeechManager(private val context: Context) {

  private var tts: TextToSpeech? = null
  private var isTtsInitialized = false
  private var speechRecognizer: SpeechRecognizer? = null

  private val _isSpeakingTts = MutableStateFlow(false)
  val isSpeakingTts: StateFlow<Boolean> = _isSpeakingTts.asStateFlow()

  private val _isListening = MutableStateFlow(false)
  val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

  private val _recognizedText = MutableStateFlow("")
  val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

  private val _audioRmsLevel = MutableStateFlow(0f)
  val audioRmsLevel: StateFlow<Float> = _audioRmsLevel.asStateFlow()

  init {
    tts = TextToSpeech(context) { status ->
      if (status == TextToSpeech.SUCCESS) {
        isTtsInitialized = true
        tts?.setSpeechRate(0.88f) // Slightly relaxed pace for learners
      } else {
        Log.w("SpeechManager", "TTS initialization returned status: $status")
      }
    }
  }

  fun speak(text: String, localeCode: String) {
    if (!isTtsInitialized || tts == null) return

    val locale = try {
      val parts = localeCode.split("-", "_")
      if (parts.size >= 2) Locale(parts[0], parts[1]) else Locale(parts[0])
    } catch (e: Exception) {
      Locale.getDefault()
    }

    try {
      tts?.language = locale
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LAGGET_TTS_${System.currentTimeMillis()}")
    } catch (e: Exception) {
      Log.e("SpeechManager", "TTS speak failed", e)
    }
  }

  fun stopSpeaking() {
    tts?.stop()
    _isSpeakingTts.value = false
  }

  fun startListening(localeCode: String, onResult: (String) -> Unit) {
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
      Log.w("SpeechManager", "Speech recognition unavailable on this device")
      return
    }

    stopListening()

    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
      setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
          _isListening.value = true
        }

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {
          _audioRmsLevel.value = (rmsdB / 10f).coerceIn(0f, 1f)
        }

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {
          _isListening.value = false
        }

        override fun onError(error: Int) {
          _isListening.value = false
          Log.w("SpeechManager", "Speech recognition error code: $error")
        }

        override fun onResults(results: Bundle?) {
          _isListening.value = false
          val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
          val bestMatch = matches?.firstOrNull() ?: ""
          _recognizedText.value = bestMatch
          if (bestMatch.isNotBlank()) {
            onResult(bestMatch)
          }
        }

        override fun onPartialResults(partialResults: Bundle?) {
          val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
          val partial = matches?.firstOrNull() ?: ""
          if (partial.isNotBlank()) {
            _recognizedText.value = partial
          }
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
      })
    }

    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
      putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
      putExtra(RecognizerIntent.EXTRA_LANGUAGE, localeCode)
      putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    try {
      speechRecognizer?.startListening(intent)
      _isListening.value = true
    } catch (e: Exception) {
      Log.e("SpeechManager", "Failed to start listening", e)
      _isListening.value = false
    }
  }

  fun stopListening() {
    try {
      speechRecognizer?.stopListening()
      speechRecognizer?.cancel()
      speechRecognizer?.destroy()
    } catch (e: Exception) {
      // Ignored
    } finally {
      speechRecognizer = null
      _isListening.value = false
    }
  }

  fun release() {
    stopListening()
    tts?.stop()
    tts?.shutdown()
    tts = null
  }
}
