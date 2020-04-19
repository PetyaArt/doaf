package com.doaf.presentation.streams_screen

import com.doaf.repository.web.NetworkRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class StreamsPresenter(private var view: StreamsFragment?) {

    private val gameIdSubject = PublishSubject.create<Int>()
    private val nextListGamesSubject = PublishSubject.create<String>()
    private val listGamesSubject = PublishSubject.create<Int>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun create() {
        val listGamesObservable= listGamesSubject
            .flatMapSingle { NetworkRepository.twitchRepository.getStreamsGame(it) }
            .map { games -> StreamsViewState.DataState(games) as StreamsViewState }
            .onErrorReturn { StreamsViewState.Error  as StreamsViewState }

        val nextListGamesObservable = nextListGamesSubject
            .distinctUntilChanged()
            .flatMapSingle { NetworkRepository.twitchRepository.getNextStreamsGame(it) }
            .map { games -> StreamsViewState.NextDateState(games) as StreamsViewState }
            .onErrorReturn { StreamsViewState.Error  as StreamsViewState }

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