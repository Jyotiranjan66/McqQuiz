package com.demo.mcqquiz.presentation.splash


sealed interface SplashEvent {
    data object LoadQuestions : SplashEvent
}

data class SplashState(
    val isLoading: Boolean = true,
    val error: String? = null
)