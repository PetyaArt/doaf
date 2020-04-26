package com.doaf.presentation.stream_player

sealed class StreamViewState {
    data class DataState(val url: String): StreamViewState()
}