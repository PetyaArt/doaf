package com.doaf.presentation.home_screen

import com.doaf.repository.web.NetworkRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class HomePresenter(private var view: HomeFragment?) {

    private val gameIdSubject = PublishSubject.create<Int>()
    private val nextListGamesSubject = PublishSubject.create<String>()
    private val listGamesSubject = PublishSubject.create<Int>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun create() {
        val listGamesObservable= listGamesSubject
            .flatMapSingle { NetworkRepository.twitchRepository.getStreamsGame(it) }
            .map { games -> HomeViewState.DataState(games) as HomeViewState }
            .onErrorReturn { HomeViewState.Error  as HomeViewState }

        val nextListGamesObservable = nextListGamesSubject
            .distinctUntilChanged()
            .flatMapSingle { NetworkRepository.twitchRepository.getNextStreamsGame(it) }
            .map { games -> HomeViewState.NextDateState(games) as HomeViewState }
            .onErrorReturn { HomeViewState.Error  as HomeViewState }

        disposable.add(
            Observable.merge(listGamesObservable, nextListGamesObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state -> view?.render(state) }
        )
    }

    fun clickStream(gameId: Int) = gameIdSubject.onNext(gameId)

    fun nextListStreams(pagination: String) = nextListGamesSubject.onNext(pagination)

    fun listStreams(gameId: Int) = listGamesSubject.onNext(gameId)

    fun destroy() {
        view = null
        disposable.clear()
    }
}