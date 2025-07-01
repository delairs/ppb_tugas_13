package com.example.tugas_13

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val score = viewModel.score
    val currentScrambledWord = viewModel.currentScrambledWord
    val currentWordCount = viewModel.currentWordCount
    val userGuess = viewModel.userGuess
    val isGameOver = viewModel.isGameOver

    if (isGameOver) {
        GameOverScreen(score = score, onPlayAgain = { viewModel.resetGame() })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tebak Kata",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Skor: $score")
            Text(text = "Kata ke: $currentWordCount dari 10")
            Text(
                text = currentScrambledWord,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Panjang kata: ${currentScrambledWord.length} huruf")
            OutlinedTextField(
                value = userGuess,
                onValueChange = { viewModel.updateUserGuess(it) },
                label = { Text("Masukkan tebakan") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.checkUserGuess() }) {
                    Text("Kirim")
                }
                Button(onClick = { viewModel.skipWord() }) {
                    Text("Lewati")
                }
            }
        }
    }
}

@Composable
fun GameOverScreen(score: Int, onPlayAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Permainan Selesai!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Skor Akhir: $score",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = onPlayAgain,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Main Lagi")
        }
    }
}