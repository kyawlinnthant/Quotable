package com.kyawlinnthant.quotable.presentation.application

import androidx.lifecycle.ViewModel
import com.kyawlinnthant.quotable.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        val navigator: Navigator,
    ) : ViewModel() {
        // todo : do splash screen
    }
