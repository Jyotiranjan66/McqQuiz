package com.demo.mcqquiz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.mcqquiz.presentation.quiz.QuizScreen
import com.demo.mcqquiz.presentation.result.ResultScreen
import com.demo.mcqquiz.presentation.splash.SplashScreen

@Composable
fun QuizNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route){

            SplashScreen(

                onNavigateToQuiz = {

                    navController.navigate(Screen.Quiz.route) {

                        popUpTo(Screen.Splash.route) {

                            inclusive = true

                        }

                    }

                }

            )

        }

        composable(Screen.Quiz.route) {

            QuizScreen(

                onNavigateToResult = {

                    navController.navigate(Screen.Result.route)

                }

            )

        }

        composable(Screen.Result.route) {
            ResultScreen(
                onRestartQuiz = {

                    navController.navigate(Screen.Quiz.route) {

                        popUpTo(Screen.Quiz.route) {

                            inclusive = true

                        }

                    }

                }

            )

        }

    }

}
