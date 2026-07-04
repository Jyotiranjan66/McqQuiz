package com.demo.mcqquiz.presentation.splash

sealed interface SplashEffect {
    data object NavigateToQuiz : SplashEffect
}