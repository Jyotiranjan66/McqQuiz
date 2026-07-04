package com.demo.mcqquiz.data.repository

import com.demo.mcqquiz.data.datasource.local.QuestionLocalDataSource
import com.demo.mcqquiz.data.mapper.toDomain
import com.demo.mcqquiz.domain.model.Question
import com.demo.mcqquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val localDataSource: QuestionLocalDataSource
) : QuestionRepository {

    override suspend fun getQuestions(): Result<List<Question>> {
        return try {
            Result.success(
                localDataSource
                    .getQuestions()
                    .map { it.toDomain() }
            )

        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}