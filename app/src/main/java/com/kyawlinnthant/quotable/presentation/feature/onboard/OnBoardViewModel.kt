package com.kyawlinnthant.quotable.presentation.feature.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.mvi.MVI
import com.kyawlinnthant.quotable.presentation.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel
    @Inject
    constructor(
        private val repository: QuoteRepository,
        private val navigator: Navigator,
    ) : ViewModel(),
        MVI<OnBoardState, OnBoardAction, OnBoardEvent> by mvi(
            initialUiState = OnBoardState.INITIAL,
        ) {
        init {
            fetchQuotes()
            getDynamicColor()
            getAppTheme()
        }

        override fun onAction(uiAction: OnBoardAction) {
            when (uiAction) {
                is OnBoardAction.OnClickAuthor -> onItemClick()
                is OnBoardAction.OnChooseDynamic -> saveDynamicColor(uiAction.enabled)
                is OnBoardAction.OnChooseTheme -> saveAppTheme(uiAction.theme)
                is OnBoardAction.OnChangeDialogState -> updateDialogState(uiAction.enabled)
            }
        }

        private fun updateDialogState(enabled: Boolean) {
            updateUiState {
                copy(shouldShowDialog = enabled)
            }
        }

        private fun saveDynamicColor(enabled: Boolean) {
            viewModelScope.launch {
                repository.saveDynamic(enabled)
            }
        }

        private fun saveAppTheme(appTheme: ThemeType) {
            viewModelScope.launch {
                repository.saveTheme(appTheme)
            }
        }

        private fun getDynamicColor() {
            viewModelScope.launch {
                repository.getDynamic().collect {
                    updateUiState {
                        copy(enabledDynamic = it)
                    }
                }
            }
        }

        private fun getAppTheme() {
            viewModelScope.launch {
                repository.getTheme().collect {
                    updateUiState {
                        copy(appTheme = it)
                    }
                }
            }
        }

        private fun fetchQuotes() {
            viewModelScope.launch {
                updateUiState {
                    copy(uiState = RequestState.Loading)
                }
                when (val response = repository.fetchRandomQuotes()) {
                    is NetworkResult.Failed -> {
                        updateUiState { copy(uiState = RequestState.Error(message = response.error)) }
                    }

                    is NetworkResult.Success -> {
                        updateUiState { copy(uiState = RequestState.Success(data = response.data)) }
                    }
                }
            }
        }

        private fun onItemClick() {
            viewModelScope.launch {
                navigator.navigate(Destination.AuthorsScreen)
            }
        }
    }
