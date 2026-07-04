package com.demo.mcqquiz.domain.model

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val skippedQuestions: Int,
    val longestStreak: Int
)