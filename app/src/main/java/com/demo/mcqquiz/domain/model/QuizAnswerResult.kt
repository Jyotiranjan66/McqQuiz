package com.demo.mcqquiz.domain.model

data class QuizAnswerResult(
    val isCorrect: Boolean,
    val newScore: Int,
    val newStreak: Int,
    val longestStreak: Int
)