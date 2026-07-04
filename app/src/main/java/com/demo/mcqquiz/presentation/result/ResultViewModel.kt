package com.demo.mcqquiz.presentation.result


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.mcqquiz.presentation.navigation.QuizSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val quizSessionManager: QuizSessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ResultEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onEvent(ResultEvent.LoadResult)
    }

    fun onEvent(event: ResultEvent) {
        when (event) {
            ResultEvent.LoadResult -> {
                loadResult()
            }

            ResultEvent.RestartQuiz -> {
                restartQuiz()
            }
        }
    }

    private fun loadResult() {
        _state.update {
            it.copy(
                isLoading = false,
                result = quizSessionManager.quizResult,
                errorMessage = if (quizSessionManager.quizResult == null)
                    "No quiz result found."
                else
                    null
            )
        }
    }

    private fun restartQuiz() {
        quizSessionManager.reset()
        viewModelScope.launch {
            _effect.send(
                ResultEffect.NavigateToQuiz
            )
        }
    }

}