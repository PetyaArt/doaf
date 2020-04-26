package com.doaf.entities


import com.google.gson.annotations.SerializedName

data class Stream(
    @SerializedName("stream")
    val stream: List<Stream>
) {
    data class Stream(
        @SerializedName("quality")
        val quality: String,
        @SerializedName("resolution")
        val resolution: String,
        @SerializedName("url")
        val url: String
    )
}