package com.doaf.entities


import com.google.gson.annotations.SerializedName

data class Games(
    @SerializedName("data")
    val data: ArrayList<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
) {
    data class Data(
        @SerializedName("box_art_url")
        val boxArtUrl: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    )

    data class Pagination(
        @SerializedName("cursor")
        var cursor: String
    )
}