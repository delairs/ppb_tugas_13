package com.example.tugas_13

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var userGuess by mutableStateOf("")
        private set
    var currentScrambledWord by mutableStateOf("")
        private set
    var score by mutableIntStateOf(0)
        private set
    var currentWordCount by mutableIntStateOf(0)
        private set
    var isGameOver by mutableStateOf(false)
        private set
    private var usedWords: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private val words = listOf(
        "kucing", "anjing", "burung", "ikan", "kelinci",
        "hamster", "tikus", "kuda", "sapi", "kambing"
    )

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        score = 0
        currentWordCount = 0
        getNextWord()
    }

    private fun getNextWord() {
        if (currentWordCount < 10) {
            var word = words.random()
            while (word in usedWords) {
                word = words.random()
            }
            currentWord = word
            currentScrambledWord = scrambleWord(word)
            usedWords.add(word)
            currentWordCount++
        } else {
            isGameOver = true
        }
    }

    private fun scrambleWord(word: String): String {
        val chars = word.toCharArray()
        do {
            chars.shuffle(Random)
        } while (String(chars) == word)
        return String(chars)
    }

    fun updateUserGuess(guess: String) {
        userGuess = guess
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            score += 10
            getNextWord()
            userGuess = ""
        }
    }

    fun skipWord() {
        score -= 5
        getNextWord()
        userGuess = ""
    }
}