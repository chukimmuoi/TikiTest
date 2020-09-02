package com.chukimuoi.tikitest.model


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("data")
    val data: List<Data> = listOf()
) {
    data class Data(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("image_url")
        val imageUrl: String = "",
        @SerializedName("mobile_url")
        val mobileUrl: String = "",
        @SerializedName("ratio")
        val ratio: Double = 0.0,
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("url")
        val url: String = ""
    )
}