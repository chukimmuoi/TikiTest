package com.chukimuoi.tikitest.model


import android.content.Context
import com.chukimuoi.tikitest.R
import com.google.gson.annotations.SerializedName
import timber.log.Timber

data class FlashDeal(
    @SerializedName("data")
    val data: List<Data> = listOf(),
    @SerializedName("tabs")
    val tabs: List<Tab> = listOf(),
    @SerializedName("version")
    val version: String = ""
) {
    data class Data(
        @SerializedName("deal_id")
        val dealId: Int = 0,
        @SerializedName("deal_status")
        val dealStatus: String = "",
        @SerializedName("deal_version")
        val dealVersion: Int = 0,
        @SerializedName("discount_percent")
        val discountPercent: Int = 0,
        @SerializedName("from_date")
        val fromDate: String = "",
        @SerializedName("product")
        val product: Product = Product(),
        @SerializedName("progress")
        val progress: Progress = Progress(),
        @SerializedName("special_from_date")
        val specialFromDate: Int = 0,
        @SerializedName("special_price")
        val specialPrice: Int = 0,
        @SerializedName("special_to_date")
        val specialToDate: Int = 0,
        @SerializedName("status")
        val status: Int = 0,
        @SerializedName("tags")
        val tags: String = "",
        @SerializedName("url")
        val url: String = ""
    ) {
        data class Product(
            @SerializedName("badges")
            val badges: List<Any> = listOf(),
            @SerializedName("discount")
            val discount: Int = 0,
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("inventory")
            val inventory: Inventory = Inventory(),
            @SerializedName("is_flower")
            val isFlower: Boolean = false,
            @SerializedName("is_fresh")
            val isFresh: Boolean = false,
            @SerializedName("is_gift_card")
            val isGiftCard: Boolean = false,
            @SerializedName("is_visible")
            val isVisible: Boolean = false,
            @SerializedName("list_price")
            val listPrice: Int = 0,
            @SerializedName("master_id")
            val masterId: Int = 0,
            @SerializedName("name")
            val name: String = "",
            @SerializedName("order_count")
            val orderCount: Int = 0,
            @SerializedName("price")
            val price: Int = 0,
            @SerializedName("price_prefix")
            val pricePrefix: String = "",
            @SerializedName("rating_average")
            val ratingAverage: Int = 0,
            @SerializedName("review_count")
            val reviewCount: Int = 0,
            @SerializedName("seller_product_id")
            val sellerProductId: Int = 0,
            @SerializedName("sku")
            val sku: Any = Any(),
            @SerializedName("thumbnail_url")
            val thumbnailUrl: String = "",
            @SerializedName("url_attendant_input_form")
            val urlAttendantInputForm: String = "",
            @SerializedName("url_path")
            val urlPath: String = ""
        ) {
            fun getPercentDiscount() : String {
                return "-${(discount * 100) / listPrice}%"
            }

            data class Inventory(
                @SerializedName("fulfillment_type")
                val fulfillmentType: String = "",
                @SerializedName("product_virtual_type")
                val productVirtualType: Any = Any()
            )
        }

        data class Progress(
            @SerializedName("percent")
            val percent: Int = 0,
            @SerializedName("qty")
            val qty: Int = 0,
            @SerializedName("qty_ordered")
            val qtyOrdered: Int = 0,
            @SerializedName("qty_remain")
            val qtyRemain: Int = 0,
            @SerializedName("status")
            val status: String = ""
        ) {
            fun getOrdered(context: Context): String {
                return if (qtyOrdered > 0) {
                    String.format(context.resources.getString(R.string.title_sold), qtyOrdered)
                } else {
                    context.resources.getString(R.string.title_just_opened_for_sale)
                }
            }
        }
    }

    data class Tab(
        @SerializedName("active")
        val active: Boolean = false,
        @SerializedName("display")
        val display: String = "",
        @SerializedName("from_date")
        val fromDate: String = "",
        @SerializedName("query_value")
        val queryValue: Int = 0,
        @SerializedName("to_date")
        val toDate: String = ""
    )
}