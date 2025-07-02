package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(mediumPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "🐾 Unscramble Zoo 🧩",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GameStatus(score = gameUiState.score)
                Text(
                    text = "🔤 Word ${gameUiState.currentWordCount}/10",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            GameLayout(
                onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
                wordCount = gameUiState.currentWordCount,
                userGuess = gameViewModel.userGuess,
                onKeyboardDone = { gameViewModel.checkUserGuess() },
                currentScrambledWord = gameUiState.currentScrambledWord,
                isGuessWrong = gameUiState.isGuessedWordWrong,
                modifier = Modifier.padding(16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Button(
                    onClick = { gameViewModel.checkUserGuess() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("✅ Guess!", fontSize = 18.sp)
                }

                OutlinedButton(
                    onClick = { gameViewModel.skipWord() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("🔁 Pass", fontSize = 18.sp)
                }
            }
        }

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFFB3E5FC),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(2.dp, Color(0xFF0288D1))
            .padding(8.dp)
    ) {
        Text(
            text = "⭐ Score: $score",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun GameLayout(
    currentScrambledWord: String,
    wordCount: Int,
    isGuessWrong: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "🔀 $currentScrambledWord",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = userGuess,
            onValueChange = onUserGuessChanged,
            label = {
                Text(
                    if (isGuessWrong) "❌ Try again!" else "💬 Type your guess",
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = isGuessWrong,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFF1F8E9),
                disabledContainerColor = Color.LightGray
            )
        )
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = {},
        title = { Text("🎉 Congratulations!") },
        text = { Text("🏁 You scored $score points!") },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text("🔁 Play Again")
            }
        },
        dismissButton = {
            TextButton(onClick = { activity.finish() }) {
                Text("🚪 Exit")
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}
