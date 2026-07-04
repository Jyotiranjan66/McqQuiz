package com.demo.mcqquiz.presentation.quiz.QuizContract

sealed interface QuizEvent {

    /**
     * Load questions from repository
     */
    data object LoadQuestions : QuizEvent

    /**
     * User taps an option
     */
    data class SelectAnswer(

        val selectedIndex: Int

    ) : QuizEvent

    /**
     * Skip current question
     */
    data object SkipQuestion : QuizEvent

    /**
     * Move to next question
     */
    data object NextQuestion : QuizEvent

    /**
     * Restart whole quiz
     */
    data object RestartQuiz : QuizEvent

}