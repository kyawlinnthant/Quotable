package com.kyawlinnthant.quotable.presentation.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.theme.AppTheme
import com.kyawlinnthant.quotable.presentation.theme.asAppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        val navigator: Navigator,
        private val isAboveS: Boolean,
        private val repository: QuoteRepository,
    ) : ViewModel() {
        val appTheme: StateFlow<AppTheme> field = MutableStateFlow<AppTheme>(AppTheme.DynamicDark)

        init {
            getAppTheme()
        }

        private fun getAppTheme() {
            viewModelScope.launch {
                repository.getTheme().collect {
                    appTheme.value = it asAppTheme isAboveS
                }
            }
        }
    }
