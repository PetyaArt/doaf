package com.doaf.presentation.discover_screen

import com.doaf.app.App
import com.doaf.presentation.Screens
import com.doaf.repository.web.NetworkRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class DiscoverPresenter(private var view: DiscoverFragment?) {
    
    private val nextListGamesSubject = PublishSubject.create<String>()
    private val listGamesSubject = PublishSubject.create<Unit>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun create() {
        val listGamesObservable= listGamesSubject
            .flatMapSingle { NetworkRepository.twitchRepository.getListGames() }
            .map { games -> DiscoverViewState.DataState(games) as DiscoverViewState }
            .onErrorReturn { DiscoverViewState.Error  as DiscoverViewState }

        val nextListGamesObservable = nextListGamesSubject
            .distinctUntilChanged()
            .flatMapSingle { NetworkRepository.twitchRepository.getNextListGames(it) }
            .map { games -> DiscoverViewState.NextDateState(games) as DiscoverViewState }
            .onErrorReturn { DiscoverViewState.Error  as DiscoverViewState }

        disposable.add(
            Observable.merge(listGamesObservable, nextListGamesObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state -> view?.render(state) }
        )
    }

    fun clickGame(gameId: Int) {
         App.application.getRouter().navigateTo(Screens.StreamsScreen(gameId))
    }

    fun nextListGames(pagination: String) = nextListGamesSubject.onNext(pagination)

    fun listGames() = listGamesSubject.onNext(Unit)

    fun destroy() {
        view = null
        disposable.clear()
    }
}