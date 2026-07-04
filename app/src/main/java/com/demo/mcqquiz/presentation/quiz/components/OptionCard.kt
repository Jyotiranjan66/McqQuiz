package com.demo.mcqquiz.presentation.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview
import com.demo.mcqquiz.presentation.theme.McqQuizTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.demo.mcqquiz.presentation.quiz.model.OptionUiState
import kotlin.math.roundToInt

@Composable
fun OptionCard(
    option: OptionUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background by animateColorAsState(

        targetValue = when {

            option.isCorrect ->
                Color(0xFF4CAF50)

            option.isWrong ->
                Color(0xFFE53935)

            option.isSelected ->
                MaterialTheme.colorScheme.primaryContainer

            else ->
                MaterialTheme.colorScheme.surface

        },

        label = ""

    )

    val border by animateColorAsState(

        targetValue = when {

            option.isCorrect ->
                Color(0xFF4CAF50)

            option.isWrong ->
                Color(0xFFE53935)

            option.isSelected ->
                MaterialTheme.colorScheme.primary

            else ->
                MaterialTheme.colorScheme.outlineVariant

        },

        label = ""

    )

    val animatedScale by animateFloatAsState(

        targetValue = when {

            option.isCorrect -> 1.06f

            option.isSelected -> 1.02f

            else -> 1f

        },

        animationSpec = spring(

            dampingRatio = Spring.DampingRatioMediumBouncy,

            stiffness = Spring.StiffnessLow

        ),

        label = ""
    )

    val elevation: Dp by animateDpAsState(

        targetValue = when {

            option.isCorrect -> 10.dp

            option.isSelected -> 8.dp

            else -> 2.dp

        },

        label = ""

    )

    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(option.isWrong) {

        if (option.isWrong) {

            repeat(4) {

                shakeOffset.animateTo(
                    -12f,
                    animationSpec = tween(40)
                )

                shakeOffset.animateTo(
                    12f,
                    animationSpec = tween(40)
                )

            }

            shakeOffset.animateTo(
                0f,
                animationSpec = tween(40)
            )

        }

    }

    Card(

        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {

                scaleX = animatedScale

                scaleY = animatedScale

            }
            .offset {

                IntOffset(

                    shakeOffset.value.roundToInt(),

                    0

                )

            }
            .clickable(

                enabled = option.enabled,

                onClick = onClick

            ),

        shape = RoundedCornerShape(18.dp),

        border = BorderStroke(2.dp, border),

        elevation = CardDefaults.cardElevation(

            defaultElevation = elevation

        ),

        colors = CardDefaults.cardColors(

            containerColor = background

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.spacedBy(14.dp)

        ) {

            Icon(

                imageVector = when {

                    option.isCorrect ->
                        Icons.Rounded.CheckCircle

                    option.isWrong ->
                        Icons.Default.Close

                    else ->
                        Icons.Outlined.CheckCircle

                },

                contentDescription = null

            )

            Text(

                modifier = Modifier.weight(1f),

                text = option.text,

                style = MaterialTheme.typography.bodyLarge

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun OptionCardPreview() {

    McqQuizTheme {

        Column(

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            OptionCard(

                option = OptionUiState(

                    index = 0,

                    text = "Flappy Bird"

                ),

                onClick = {}

            )

            OptionCard(

                option = OptionUiState(

                    index = 1,

                    text = "Paris",

                    isSelected = true

                ),

                onClick = {}

            )

            OptionCard(

                option = OptionUiState(

                    index = 2,

                    text = "Berlin",

                    isWrong = true

                ),

                onClick = {}

            )

            OptionCard(

                option = OptionUiState(

                    index = 3,

                    text = "Rome",

                    isCorrect = true

                ),

                onClick = {}

            )

        }

    }

}