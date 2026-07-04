package com.demo.mcqquiz.presentation.navigation

import com.demo.mcqquiz.domain.model.Question
import com.demo.mcqquiz.domain.model.QuizResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizSessionManager @Inject constructor() {

    var questions: List<Question> = emptyList()

    var quizResult: QuizResult? = null

    fun reset() {
        questions = emptyList()
        quizResult = null
    }
}