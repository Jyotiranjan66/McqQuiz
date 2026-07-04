package com.demo.mcqquiz.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SplashScreen(
    onNavigateToQuiz: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    var visible by remember {
        mutableStateOf(false)
    }

    val logoScale by animateFloatAsState(
        targetValue = if (visible) 1f else .6f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    LaunchedEffect(Unit) {
        visible = true
        viewModel.effect.collect {
            if (it == SplashEffect.NavigateToQuiz) {
                onNavigateToQuiz()
            }
        }

    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = tween(600)
                ) + scaleIn( animationSpec = tween(800))
            ) {

                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .scale(logoScale),
                    tint = MaterialTheme.colorScheme.primary
                )

            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "MCQ Quiz",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Sharpen Your Android Knowledge",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .height(6.dp),

                progress = {
                    if (state.isLoading) .55f else 1f
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            CircularProgressIndicator()
        }

    }

}
