package com.demo.mcqquiz.presentation.quiz.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.mcqquiz.presentation.theme.McqQuizTheme

@Composable
fun StreakBadge(

    streak: Int,

    modifier: Modifier = Modifier

) {

    val infinite = rememberInfiniteTransition(label = "")

    val pulse by infinite.animateFloat(

        initialValue = 1f,

        targetValue = if (streak >= 3) 1.08f else 1f,

        animationSpec = infiniteRepeatable(

            animation = tween(700),

            repeatMode = RepeatMode.Reverse

        ),

        label = ""

    )

    val background by animateColorAsState(

        targetValue = when {

            streak >= 5 -> Color(0xFFFFC107)

            streak >= 3 -> Color(0xFFFF9800)

            else -> MaterialTheme.colorScheme.surfaceVariant

        },

        animationSpec = spring(),

        label = ""

    )

    val contentColor by animateColorAsState(

        targetValue = when {

            streak >= 3 -> Color.White

            else -> MaterialTheme.colorScheme.onSurfaceVariant

        },

        label = ""

    )

    Surface(

        modifier = modifier.scale(pulse),

        color = background,

        shape = CircleShape,

        tonalElevation = 6.dp,

        shadowElevation = if (streak >= 3) 10.dp else 2.dp

    ) {

        Row(

            modifier = Modifier.padding(

                horizontal = 18.dp,

                vertical = 10.dp

            ),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            Icon(

                imageVector = if (streak >= 5)
                    Icons.Rounded.EmojiEvents
                else
                    Icons.Rounded.LocalFireDepartment,

                contentDescription = null,

                tint = contentColor,

                modifier = Modifier.size(24.dp)

            )

            Column {

                Text(

                    text = when {

                        streak >= 5 -> "Quiz Master"

                        streak >= 3 -> "On Fire!"

                        else -> "Keep Going"

                    },

                    style = MaterialTheme.typography.titleSmall,

                    fontWeight = FontWeight.Bold,

                    color = contentColor

                )

                Text(

                    text = "Current Streak : $streak",

                    style = MaterialTheme.typography.bodySmall,

                    color = contentColor.copy(alpha = .85f)

                )

            }

        }

    }

}

@Preview
@Composable
private fun StreakBadgePreview() {

    McqQuizTheme {

        StreakBadge(

            streak = 4

        )
    }
}