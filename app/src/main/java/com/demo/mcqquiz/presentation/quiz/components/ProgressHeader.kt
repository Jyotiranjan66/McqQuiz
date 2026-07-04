package com.demo.mcqquiz.presentation.quiz.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.demo.mcqquiz.presentation.quiz.QuizState

@Composable
fun ProgressHeader(
    state: QuizState,
    modifier: Modifier = Modifier
) {

    val progress by animateFloatAsState(

        targetValue = state.progress,

        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,

        label = ""
    )

    Card(

        modifier = modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.surfaceVariant

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 4.dp

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Column {

                    Text(

                        text = "Question",

                        style = MaterialTheme.typography.labelMedium,

                        color = MaterialTheme.colorScheme.primary

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "${state.currentQuestionIndex + 1} / ${state.questions.size}",

                        style = MaterialTheme.typography.headlineSmall,

                        fontWeight = FontWeight.Bold

                    )

                }

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    CircularProgressIndicator(

                        progress = { progress },

                        modifier = Modifier.size(42.dp),

                        strokeWidth = 5.dp

                    )

                    Spacer(

                        modifier = Modifier.width(12.dp)

                    )

                    Text(

                        text = "${(progress * 100).toInt()}%",

                        style = MaterialTheme.typography.titleMedium,

                        fontWeight = FontWeight.Bold

                    )

                }

            }

            Spacer(modifier = Modifier.height(18.dp))

            LinearProgressIndicator(

                progress = { progress },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

        }

    }

}