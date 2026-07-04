package com.demo.mcqquiz.presentation.result


import com.demo.mcqquiz.domain.model.QuizResult

data class ResultState(

    val isLoading: Boolean = false,

    val result: QuizResult? = null,

    val errorMessage: String? = null

) {

    val scorePercentage: Float

        get() =

            result?.let {

                if (it.totalQuestions == 0)
                    0f
                else
                    it.correctAnswers.toFloat() / it.totalQuestions

            } ?: 0f

}

sealed interface ResultEvent {

    data object LoadResult : ResultEvent

    data object RestartQuiz : ResultEvent

}

sealed interface ResultEffect {

    data object NavigateToQuiz : ResultEffect

}