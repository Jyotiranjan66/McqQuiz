package com.demo.mcqquiz.domain.usecase

import com.demo.mcqquiz.domain.model.QuizResult
import javax.inject.Inject

class CalculateQuizResultUseCase @Inject constructor() {

    operator fun invoke(
        totalQuestions: Int,
        correctAnswers: Int,
        skippedQuestions: Int,
        longestStreak: Int
    ): QuizResult {

        return QuizResult(
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            skippedQuestions = skippedQuestions,
            longestStreak = longestStreak
        )
    }
}