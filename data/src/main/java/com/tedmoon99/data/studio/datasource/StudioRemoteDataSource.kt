package com.tedmoon99.data.studio.datasource

import com.tedmoon99.data.studio.model.StudioDto

interface StudioRemoteDataSource {

    suspend fun getStudio(conceptId: Int, page: Int): StudioDto

    suspend fun getConceptName(conceptId: Int): String

}