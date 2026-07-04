package com.demo.mcqquiz.presentation.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.unit.dp
import com.demo.mcqquiz.presentation.quiz.QuizContract.QuizEffect
import com.demo.mcqquiz.presentation.quiz.QuizContract.QuizEvent
import com.demo.mcqquiz.presentation.quiz.components.ProgressHeader
import com.demo.mcqquiz.presentation.quiz.components.QuestionCard
import com.demo.mcqquiz.presentation.quiz.components.StreakBadge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.mcqquiz.presentation.quiz.components.OptionCard
import com.demo.mcqquiz.presentation.quiz.components.SkipButton
import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.ui.platform.LocalView

@Composable
fun QuizScreen(
    onNavigateToResult: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val view = LocalView.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                QuizEffect.NavigateToResult -> {
                    onNavigateToResult()
                }

                is QuizEffect.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        effect.message
                    )
                }

                QuizEffect.CorrectAnswer -> {
                    view.performHapticFeedback(
                        HapticFeedbackConstants.CONFIRM
                    )
                }

                QuizEffect.WrongAnswer -> {
                    view.performHapticFeedback(
                        HapticFeedbackConstants.REJECT
                    )
                }

                is QuizEffect.CelebrateStreak -> {
                    snackbarHostState.showSnackbar(
                        "🔥 ${effect.streak} Correct Answers!"
                    )
                }
            }
        }

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }

    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingScreen()
            }

            state.errorMessage != null -> {
                ErrorScreen(message = state.errorMessage!!)
            }

            else -> {
                AnimatedVisibility(
                    visible = !state.isLoading,

                    enter = fadeIn(
                        animationSpec = tween(500)
                    ) + slideInVertically(
                        animationSpec = tween(500),
                        initialOffsetY = { it / 4 }
                    )
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .pointerInput(Unit) {

                                var totalDrag = 0f
                                detectHorizontalDragGestures(

                                    onHorizontalDrag = { _, dragAmount ->
                                        totalDrag += dragAmount
                                    },
                                    onDragEnd = {
                                        if (totalDrag > 140f && !state.showAnswer) {

                                            viewModel.onEvent(
                                                QuizEvent.SkipQuestion
                                            )
                                        }
                                        totalDrag = 0f
                                    }
                                )
                            },

                        contentPadding = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 20.dp
                        ),

                        verticalArrangement = Arrangement.spacedBy(20.dp)

                    ) {

                        item {
                            ProgressHeader(state = state)
                        }

                        item {
                            StreakBadge(streak = state.currentStreak)
                        }

                        item { HorizontalDivider() }

                        state.currentQuestion?.let { _ ->
                            item {
                                AnimatedContent(
                                    targetState = state.currentQuestion,
                                    transitionSpec = {
                                        slideInHorizontally(
                                            animationSpec = tween(450),

                                            initialOffsetX = { fullWidth -> fullWidth }) +
                                                fadeIn() togetherWith slideOutHorizontally(
                                                    animationSpec = tween(450),
                                                    targetOffsetX = { fullWidth -> -fullWidth }
                                                ) +

                                                fadeOut() using SizeTransform(

                                            clip = false
                                        )
                                    },

                                    label = "Question Transition"

                                ) { question ->

                                    question?.let {
                                        QuestionCard(question = it)
                                    }

                                }

                            }

                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(
                            count = state.optionStates.size,
                            key = { index -> state.optionStates[index].index }
                        ) { index ->

                            val option = state.optionStates[index]

                            AnimatedVisibility(

                                visible = true,

                                enter =

                                    fadeIn(

                                        animationSpec = tween(

                                            durationMillis = 350,

                                            delayMillis = option.index * 90

                                        )

                                    )

                            ) {

                                OptionCard(

                                    option = option,

                                    onClick = {

                                        if (!state.showAnswer) {

                                            viewModel.onEvent(

                                                QuizEvent.SelectAnswer(

                                                    option.index

                                                )

                                            )

                                        }

                                    }

                                )

                            }
                        }

                        item {

                            Spacer(

                                modifier = Modifier.height(12.dp)

                            )

                        }

                        item {

                            SkipButton(

                                enabled = !state.showAnswer,

                                onSkip = {

                                    viewModel.onEvent(

                                        QuizEvent.SkipQuestion

                                    )

                                }

                            )

                        }

                        item {

                            Spacer(

                                modifier = Modifier.height(32.dp)

                            )

                        }

                    }

                }

            }

        }

    }

}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    message: String
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}