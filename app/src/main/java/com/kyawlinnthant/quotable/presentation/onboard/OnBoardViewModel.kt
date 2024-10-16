package com.kyawlinnthant.quotable.presentation.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    val state: StateFlow<OnBoardViewModelState>
        field = MutableStateFlow(OnBoardViewModelState())

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            state.value = state.value.copy(
                uiState = OnBoardUiState.Loading
            )
            when (val response = repository.randomQuotes()) {
                is NetworkResult.Failed -> {
                    state.value = state.value.copy(
                        uiState = OnBoardUiState.Error(response.error)
                    )
                }

                is NetworkResult.Success -> {
                    state.value = state.value.copy(
                        uiState = OnBoardUiState.Success(response.data)
                    )
                }
            }
        }
    }
}