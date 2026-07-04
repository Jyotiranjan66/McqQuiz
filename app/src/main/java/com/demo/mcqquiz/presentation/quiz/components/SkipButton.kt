package com.demo.mcqquiz.presentation.quiz.components

import androidx.compose.material.icons.rounded.Clear
import androidx.compose.ui.tooling.preview.Preview
import com.demo.mcqquiz.presentation.theme.McqQuizTheme
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun SkipButton(

    enabled: Boolean,

    onSkip: () -> Unit,

    modifier: Modifier = Modifier

) {

    val transition = rememberInfiniteTransition(label = "")

    val scale by transition.animateFloat(

        initialValue = 1f,

        targetValue = if (enabled) 1.04f else 1f,

        animationSpec = infiniteRepeatable(

            animation = tween(900),

            repeatMode = RepeatMode.Reverse

        ),

        label = ""

    )

    OutlinedButton(

        onClick = onSkip,

        enabled = enabled,

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .scale(scale),

        colors = ButtonDefaults.outlinedButtonColors(

            contentColor = MaterialTheme.colorScheme.primary
        )

    ) {

        Icon(

            imageVector = Icons.Rounded.Clear,

            contentDescription = null
        )

        Text(

            text = "Skip Question",

            modifier = Modifier.weight(1f)

        )

        Icon(
            imageVector = Icons.Rounded.ArrowForward,
            contentDescription = null
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun SkipButtonPreview() {

    McqQuizTheme {

        SkipButton(

            enabled = true,

            onSkip = {}

        )

    }

}