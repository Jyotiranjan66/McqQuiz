package com.demo.mcqquiz.data.datasource.local

import com.demo.mcqquiz.data.model.QuestionDto

interface QuestionLocalDataSource {
    suspend fun getQuestions(): List<QuestionDto>
}