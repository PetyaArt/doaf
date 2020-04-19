package com.doaf.presentation.main_screen

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class MainPresenter(private var view: MainActivity?) {

    private val homeSubject = PublishSubject.create<MainViewState.HomeState>()
    private val discoverSubject = PublishSubject.create<MainViewState.DiscoverState>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun create() {
        disposable.add(Observable.merge(homeSubject, discoverSubject)
            .subscribe { state ->
                view?.render(when(state) {
                    is MainViewState.HomeState -> MainViewState.HomeState
                    is MainViewState.DiscoverState -> MainViewState.DiscoverState
                })
            })
        clickHome()
    }

    fun destroy() {
        view = null
        disposable.clear()
    }

    fun clickHome() = homeSubject.onNext(MainViewState.HomeState)

    fun clickDiscover() = discoverSubject.onNext(MainViewState.DiscoverState)

}