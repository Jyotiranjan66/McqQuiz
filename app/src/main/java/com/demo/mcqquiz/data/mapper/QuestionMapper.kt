package com.demo.mcqquiz.data.mapper

import com.demo.mcqquiz.data.model.QuestionDto
import com.demo.mcqquiz.domain.model.Question

fun QuestionDto.toDomain(): Question {
    return Question(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex
    )
}