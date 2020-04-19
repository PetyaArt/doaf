package com.doaf.presentation.discover_screen

import com.doaf.entities.Games

sealed class DiscoverViewState {
    data class DataState(val data: Games): DiscoverViewState()
    data class NextDateState(val data: Games): DiscoverViewState()
    object Error: DiscoverViewState()
}