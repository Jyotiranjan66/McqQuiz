package com.demo.mcqquiz.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.demo.mcqquiz.presentation.navigation.QuizNavGraph
import com.demo.mcqquiz.presentation.theme.McqQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            McqQuizTheme {
                QuizNavGraph()
            }
        }
    }
}