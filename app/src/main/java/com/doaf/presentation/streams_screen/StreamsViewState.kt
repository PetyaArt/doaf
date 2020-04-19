package com.doaf.presentation.streams_screen

import com.doaf.entities.Streams

sealed class StreamsViewState {
    data class DataState(val data: Streams): StreamsViewState()
    data class NextDateState(val data: Streams): StreamsViewState()
    object Error: StreamsViewState()
}