package com.tedmoon99.data.studio.datasource

import com.tedmoon99.data.studio.model.StudioDto
import com.tedmoon99.domain.home.model.ConceptEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudioService {
    // -------- 해당 컨셉 스튜디오 API --------

    // 컨셉 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios") // baseUrl 뒤 상대경로
    suspend fun getStudio(
        @Path("conceptId") conceptId: Int, // 동적 경로 파라미터
        @Query("page") page: Int // 쿼리 파라미터
    ): StudioDto


    // 필터 적용 후 스튜디오 목록 조회
    @GET("v1/concepts/{conceptId}/studios/filters")
    suspend fun filterStudio(
        @Path("conceptId") conceptId: Int,
        @Query("page") page: Int,
        @Query("price") price: Int?,
        @Query("rating") rating: Double?,
        @Query("locations") locations: List<String>?,
    ): StudioDto

    // 컨셉 조회
    @GET("v1/concepts")
    suspend fun getConcept(): List<ConceptEntity>
}