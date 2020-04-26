package com.doaf.presentation.stream_player

import android.util.Log
import com.doaf.repository.web.NetworkRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class StreamPresenter(private var view: StreamFragment?) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun create(nickname: String) {
        disposable.add(
            NetworkRepository.streamRepository.getStream(nickname)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    view?.render(StreamViewState.DataState(it.stream[0].url))
                }, {
                    Log.d("logs", it.message)
                })
        )
    }

    fun destroy() {
        view = null
        disposable.dispose()
    }
}