package com.doaf.presentation.main_screen

sealed class MainViewState {
    object HomeState : MainViewState()
    object DiscoverState: MainViewState()
}