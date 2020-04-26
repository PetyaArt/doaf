package com.doaf.repository.web

import com.doaf.entities.Stream
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitchStreamRepository {

    @GET(".")
    fun getStream(@Query("nickname") nickname: String): Single<Stream>
}