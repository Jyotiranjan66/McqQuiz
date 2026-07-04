package com.demo.mcqquiz.presentation.quiz.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.demo.mcqquiz.domain.model.Question

@Composable
fun QuestionCard(

    question: Question,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 8.dp

        ),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.surface

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            Box(

                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(18.dp),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = Icons.Default.Psychology,

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.primary

                )

            }

            Surface(

                shape = RoundedCornerShape(50),

                color = MaterialTheme.colorScheme.secondaryContainer

            ) {

                Text(

                    modifier = Modifier.padding(

                        horizontal = 16.dp,

                        vertical = 6.dp

                    ),

                    text = "Question #${question.id}",

                    style = MaterialTheme.typography.labelLarge,

                    color = MaterialTheme.colorScheme.onSecondaryContainer

                )

            }

            AnimatedContent(

                targetState = question.question,

                transitionSpec = {

                    slideInHorizontally(

                        animationSpec = tween(400),

                        initialOffsetX = { it }

                    ) +

                            fadeIn() togetherWith

                    slideOutHorizontally(

                        animationSpec = tween(400),

                        targetOffsetX = { -it }

                    ) +

                            fadeOut() using SizeTransform(

                            clip = false

                            )

                },

                label = "Question"

            ) { text ->

                Text(

                    text = text,

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    textAlign = TextAlign.Center,

                    color = MaterialTheme.colorScheme.onSurface

                )

            }

        }

    }

}