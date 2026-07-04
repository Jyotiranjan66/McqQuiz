package com.demo.mcqquiz.domain.usecase

import com.demo.mcqquiz.domain.model.Question
import com.demo.mcqquiz.domain.model.QuizAnswerResult
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor() {

    operator fun invoke(
        question: Question,
        selectedIndex: Int,
        currentScore: Int,
        currentStreak: Int,
        longestStreak: Int
    ): QuizAnswerResult {

        val isCorrect = selectedIndex == question.correctOptionIndex

        val newScore =
            if (isCorrect) currentScore + 1
            else currentScore

        val newStreak =
            if (isCorrect) currentStreak + 1
            else 0

        val highest =
            maxOf(longestStreak, newStreak)

        return QuizAnswerResult(
            isCorrect = isCorrect,
            newScore = newScore,
            newStreak = newStreak,
            longestStreak = highest
        )
    }

}