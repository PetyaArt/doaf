package com.doaf.presentation

import com.doaf.presentation.discover_screen.DiscoverFragment
import com.doaf.presentation.home_screen.HomeFragment
import com.doaf.presentation.stream_player.StreamFragment
import com.doaf.presentation.streams_screen.StreamsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object HomeScreen : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    object DiscoverScreen : SupportAppScreen() {
        override fun getFragment() = DiscoverFragment()
    }

    data class StreamsScreen(val gameID: Int) : SupportAppScreen() {
        override fun getFragment() = StreamsFragment.newInstance(gameID)
    }

    object StreamScreen : SupportAppScreen() {
        override fun getFragment() = StreamFragment()
    }

}