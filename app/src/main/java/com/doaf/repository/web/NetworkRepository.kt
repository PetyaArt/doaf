package com.doaf.repository.web

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.twitch.tv/helix/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    val twitchRepository = retrofit.create(TwitchRepository::class.java)


    private val retrofit2 = Retrofit.Builder()
        .baseUrl("http://161.35.72.139/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    val streamRepository = retrofit2.create(TwitchStreamRepository::class.java)

}
