package com.tedmoon99.data.studio.model


import com.google.gson.annotations.SerializedName
import com.tedmoon99.data.common.paging.model.Pageable
import com.tedmoon99.data.common.paging.model.SortX

data class StudioDto(
    @SerializedName("content")
    val content: List<Studio>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: SortX,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)