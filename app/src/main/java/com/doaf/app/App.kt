package com.doaf.app

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {

    private lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        application = this
        init()
    }

    private fun init() {
        cicerone = Cicerone.create()
    }

    fun getRouter() = cicerone.router

    fun getNavigator() = cicerone.navigatorHolder

    companion object {
        lateinit var application: App
    }
}