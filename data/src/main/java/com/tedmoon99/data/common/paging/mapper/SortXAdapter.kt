package com.tedmoon99.data.common.paging.mapper

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.tedmoon99.data.common.paging.model.SortX
import java.lang.reflect.Type

class SortXAdapter: JsonDeserializer<SortX> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SortX {
        return if (json.isJsonObject) {
            // 정상 파싱
            context.deserialize(json, SortX::class.java)
        } else {
            // 배열인 경우 기본값 반환
            SortX(empty = true, sorted = false, unsorted = true)
        }
    }
}