package com.demo.mcqquiz.domain.usecase

import javax.inject.Inject

class SkipQuestionUseCase @Inject constructor() {

    operator fun invoke(
        skippedQuestions: Int
    ): Int {
        return skippedQuestions + 1
    }
}