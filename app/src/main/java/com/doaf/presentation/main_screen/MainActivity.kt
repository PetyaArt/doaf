package com.doaf.presentation.main_screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.doaf.R
import com.doaf.app.App
import com.doaf.presentation.Screens
import com.doaf.presentation.ViewState
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), ViewState<MainViewState>,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.main_container)
    private var presenter: MainPresenter? = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_sheet.setOnNavigationItemSelectedListener(this)
        presenter?.create()
    }

    override fun onResume() {
        super.onResume()
        App.application.getNavigator().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.application.getNavigator().removeNavigator()
    }

    override fun render(state: MainViewState) {
        when(state) {
            is MainViewState.HomeState -> App.application.getRouter().replaceScreen(Screens.HomeScreen)
            is MainViewState.DiscoverState -> App.application.getRouter().replaceScreen(Screens.DiscoverScreen)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> presenter?.clickHome()
            R.id.discover -> presenter?.clickDiscover()
        }
        return true
    }

    override fun onDestroy() {
        presenter?.destroy()
        presenter = null
        super.onDestroy()
    }
}
