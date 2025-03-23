package com.tedmoon99.domain.studio.repository

import androidx.paging.PagingData
import com.tedmoon99.domain.studio.model.StudioEntity
import kotlinx.coroutines.flow.Flow


interface StudioRepository {

    suspend fun getStudio(conceptId: Int): Flow<PagingData<StudioEntity>>

    suspend fun getConceptName(conceptId: Int): String
}