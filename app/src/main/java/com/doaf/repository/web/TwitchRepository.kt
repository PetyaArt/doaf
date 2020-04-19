package com.doaf.repository.web

import com.doaf.entities.Games
import com.doaf.entities.Streams
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TwitchRepository {

    @Headers("Client-ID: 4ivl9aewsji4jczdl0iad5e7su681p")
    @GET("games/top")
    fun getListGames(): Single<Games>

    @Headers("Client-ID: 4ivl9aewsji4jczdl0iad5e7su681p")
    @GET("games/top")
    fun getNextListGames(@Query("after") pagination: String): Single<Games>

    @Headers("Client-ID: 4ivl9aewsji4jczdl0iad5e7su681p")
    @GET("streams")
    fun getStreamsGame(@Query("game_id") game_id: Int): Single<Streams>

    @Headers("Client-ID: 4ivl9aewsji4jczdl0iad5e7su681p")
    @GET("streams")
    fun getNextStreamsGame(@Query("after") pagination: String): Single<Streams>

}
