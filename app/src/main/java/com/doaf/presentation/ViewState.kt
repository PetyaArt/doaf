package com.doaf.presentation

interface ViewState<T> {
    fun render(state: T)
}