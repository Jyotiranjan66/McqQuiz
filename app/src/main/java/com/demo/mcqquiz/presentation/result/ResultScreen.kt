package com.demo.mcqquiz.presentation.result


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ResultScreen(
    onRestartQuiz: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ResultEffect.NavigateToQuiz -> {
                    onRestartQuiz()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        state.result?.let { result ->
            val animatedProgress by animateFloatAsState(
                targetValue = state.scorePercentage,
                animationSpec = tween(1200),
                label = ""
            )

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = tween(600)
                ) + slideInVertically(

                    animationSpec = tween(600)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Text(
                        text = "Quiz Completed 🎉",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.height(180.dp),
                            strokeWidth = 12.dp
                        )

                        Text(
                            text = "${(animatedProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    ResultCard(
                        icon = Icons.Default.CheckCircle,
                        title = "Correct Answers",
                        value = "${result.correctAnswers}/${result.totalQuestions}"
                    )

                    ResultCard(
                        icon = Icons.Default.EmojiEvents,
                        title = "Longest Streak",
                        value = "${result.longestStreak}"
                    )

                    ResultCard(
                        icon = Icons.Default.SkipNext,
                        title = "Skipped",
                        value = "${result.skippedQuestions}"
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onEvent(ResultEvent.RestartQuiz)
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )

                        Spacer(
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = "Restart Quiz"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultCard(

    icon: ImageVector,

    title: String,

    value: String

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }

}