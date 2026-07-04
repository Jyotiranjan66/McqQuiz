package com.demo.mcqquiz.presentation.quiz

import com.demo.mcqquiz.domain.model.Question
import com.demo.mcqquiz.presentation.quiz.model.OptionUiState

data class QuizState(

    val isLoading: Boolean = true,

    val questions: List<Question> = emptyList(),

    val currentQuestionIndex: Int = 0,

    val selectedAnswerIndex: Int? = null,

    val showAnswer: Boolean = false,

    val score: Int = 0,

    val currentStreak: Int = 0,

    val highestStreak: Int = 0,

    val skippedQuestions: Int = 0,

    val isQuizCompleted: Boolean = false,

    val errorMessage: String? = null

) {

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val progress: Float
        get() =
            if (questions.isEmpty()) 0f
            else (currentQuestionIndex + 1).toFloat() / questions.size

    val questionCounter: String
        get() = "Question ${currentQuestionIndex + 1} of ${questions.size}"

    val canSelectAnswer: Boolean
        get() = !showAnswer

    val optionStates: List<OptionUiState>
        get() {

            val question = currentQuestion ?: return emptyList()

            return question.options.mapIndexed { index, option ->

                OptionUiState(

                    index = index,

                    text = option,

                    isSelected = selectedAnswerIndex == index,

                    isCorrect = showAnswer &&
                            question.correctOptionIndex == index,

                    isWrong = showAnswer &&
                            selectedAnswerIndex == index &&
                            question.correctOptionIndex != index,

                    enabled = !showAnswer

                )

            }

        }

}
