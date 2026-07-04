package com.demo.mcqquiz.domain.usecase
import com.demo.mcqquiz.domain.model.Question
import com.demo.mcqquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(): Result<List<Question>> {
        return repository.getQuestions()
    }
}