package com.demo.mcqquiz.presentation.quiz.model

data class OptionUiState(

    val index: Int,

    val text: String,

    val isSelected: Boolean = false,

    val isCorrect: Boolean = false,

    val isWrong: Boolean = false,

    val enabled: Boolean = true

)

