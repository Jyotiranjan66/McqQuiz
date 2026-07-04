package com.demo.mcqquiz.presentation.quiz.QuizContract


sealed interface QuizEffect {

    /**
     * Navigate to Result Screen
     */
    data object NavigateToResult : QuizEffect

    /**
     * Show snackbar
     */
    data class ShowMessage(

        val message: String

    ) : QuizEffect

    /**
     * Play green animation
     */
    data object CorrectAnswer : QuizEffect

    /**
     * Shake animation
     */
    data object WrongAnswer : QuizEffect

    /**
     * Celebrate streak
     */
    data class CelebrateStreak(

        val streak: Int

    ) : QuizEffect

}