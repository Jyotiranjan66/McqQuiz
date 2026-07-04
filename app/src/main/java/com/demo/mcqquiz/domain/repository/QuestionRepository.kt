package com.demo.mcqquiz.domain.repository

import com.demo.mcqquiz.domain.model.Question

interface QuestionRepository {
    suspend fun getQuestions(): Result<List<Question>>
}