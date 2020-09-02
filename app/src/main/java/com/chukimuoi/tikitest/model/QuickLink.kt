package com.chukimuoi.tikitest.model


import com.google.gson.annotations.SerializedName

data class QuickLink(
    @SerializedName("data")
    val data: List<List<Data>> = listOf()
) {
    data class Data(
        @SerializedName("authentication")
        val authentication: Boolean = false,
        @SerializedName("content")
        val content: String = "",
        @SerializedName("image_url")
        val imageUrl: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("web_url")
        val webUrl: String = ""
    )
}