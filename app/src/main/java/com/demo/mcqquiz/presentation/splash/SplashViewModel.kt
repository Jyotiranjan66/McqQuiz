package com.demo.mcqquiz.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.mcqquiz.domain.usecase.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

    private val getQuestionsUseCase: GetQuestionsUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())

    val state = _state.asStateFlow()

    private val _effect = Channel<SplashEffect>()

    val effect = _effect.receiveAsFlow()

    init {
        onEvent(SplashEvent.LoadQuestions)
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.LoadQuestions -> {
                loadQuestions()
            }
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(2000)

            val result = getQuestionsUseCase()
            result.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }

                _effect.send(SplashEffect.NavigateToQuiz)
            }

            result.onFailure {failure->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = failure.message
                    )
                }

            }

        }

    }

}
