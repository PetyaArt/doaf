package com.doaf.presentation.home_screen

import com.doaf.entities.Streams

sealed class HomeViewState {
    data class DataState(val data: Streams): HomeViewState()
    data class NextDateState(val data: Streams): HomeViewState()
    object Error: HomeViewState()
}