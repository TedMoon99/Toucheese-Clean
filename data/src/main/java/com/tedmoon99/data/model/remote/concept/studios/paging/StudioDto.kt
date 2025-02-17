package com.tedmoon99.data.model.remote.concept.studios.paging


import com.google.gson.annotations.SerializedName
import com.tedmoon99.data.model.remote.concept.studios.content.Studio

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