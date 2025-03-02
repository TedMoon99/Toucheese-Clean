package com.tedmoon99.data.datasource.remote.studio

import com.tedmoon99.data.model.remote.concept.studios.paging.StudioDto

interface StudioRemoteDataSource {

    suspend fun getStudio(conceptId: Int, page: Int): StudioDto

    suspend fun getConceptName(conceptId: Int): String

}