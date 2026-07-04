package com.demo.mcqquiz.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.mcqquiz.domain.usecase.CalculateQuizResultUseCase
import com.demo.mcqquiz.domain.usecase.GetQuestionsUseCase
import com.demo.mcqquiz.domain.usecase.SkipQuestionUseCase
import com.demo.mcqquiz.domain.usecase.SubmitAnswerUseCase
import com.demo.mcqquiz.presentation.navigation.QuizSessionManager
import com.demo.mcqquiz.presentation.quiz.QuizContract.QuizEffect
import com.demo.mcqquiz.presentation.quiz.QuizContract.QuizEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val skipQuestionUseCase: SkipQuestionUseCase,
    private val calculateQuizResultUseCase: CalculateQuizResultUseCase,
    private val quizSessionManager: QuizSessionManager

) : ViewModel() {
    private val _state = MutableStateFlow(QuizState())

    val state = _state.asStateFlow()

    private val _effect = Channel<QuizEffect>()

    val effect = _effect.receiveAsFlow()

    init {
        onEvent(QuizEvent.LoadQuestions)
    }

    fun onEvent(event: QuizEvent) {
        when (event) {

            QuizEvent.LoadQuestions ->
                loadQuestions()

            is QuizEvent.SelectAnswer ->
                submitAnswer(event.selectedIndex)

            QuizEvent.SkipQuestion ->
                skipQuestion()

            QuizEvent.NextQuestion ->
                moveToNextQuestion()

            QuizEvent.RestartQuiz ->
                restartQuiz()

        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = getQuestionsUseCase()

            result.onSuccess { questions ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        questions = questions
                    )
                }
            }

            result.onFailure {failure->
                _state.update {
                    it.copy(
                        isLoading = false, errorMessage = failure.message
                    )
                }
            }
        }
    }

    private fun submitAnswer(
        selectedIndex: Int
    ) {
        val state = _state.value

        val question = state.currentQuestion ?: return

        if (state.showAnswer) return

        val answerResult =
            submitAnswerUseCase(
                question = question,
                selectedIndex = selectedIndex,
                currentScore = state.score,
                currentStreak = state.currentStreak,
                longestStreak = state.highestStreak
            )

        _state.update {
            it.copy(
                selectedAnswerIndex = selectedIndex,
                showAnswer = true,
                score = answerResult.newScore,
                currentStreak = answerResult.newStreak,
                highestStreak = answerResult.longestStreak
            )
        }

        viewModelScope.launch {
            if (answerResult.isCorrect) {
                _effect.send(QuizEffect.CorrectAnswer)

                if (answerResult.newStreak >= 3) {
                    _effect.send(
                        QuizEffect.CelebrateStreak(answerResult.newStreak)
                    )
                }
            } else {
                _effect.send(QuizEffect.WrongAnswer)
            }
            delay(1000)
            onEvent(QuizEvent.NextQuestion)
        }
    }

    private fun skipQuestion() {
        val skipped =
            skipQuestionUseCase(_state.value.skippedQuestions)

        _state.update {
            it.copy(
                skippedQuestions = skipped,
                selectedAnswerIndex = null,
                showAnswer = false
            )
        }
        moveToNextQuestion()
    }

    private fun moveToNextQuestion() {
        val currentState = _state.value
        val nextIndex = currentState.currentQuestionIndex + 1
        if (nextIndex >= currentState.questions.size) {
            finishQuiz()
            return
        }

        _state.update {
            it.copy(
                currentQuestionIndex = nextIndex,
                selectedAnswerIndex = null, showAnswer = false
            )
        }
    }

    private fun finishQuiz() {
        val state = _state.value

        quizSessionManager.quizResult =
            calculateQuizResultUseCase(
                totalQuestions = state.questions.size,
                correctAnswers = state.score,
                skippedQuestions = state.skippedQuestions,
                longestStreak = state.highestStreak
            )

        _state.update {
            it.copy(isQuizCompleted = true)
        }

        viewModelScope.launch {
            _effect.send(QuizEffect.NavigateToResult)
        }
    }
    private fun restartQuiz() {

        _state.update {
            it.copy(
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                showAnswer = false,
                score = 0,
                currentStreak = 0,
                highestStreak = 0,
                skippedQuestions = 0,
                isQuizCompleted = false,
                errorMessage = null
            )
        }
    }
}