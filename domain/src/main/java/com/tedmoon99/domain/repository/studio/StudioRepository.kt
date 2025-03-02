package com.tedmoon99.domain.repository.studio

import androidx.paging.PagingData
import com.tedmoon99.domain.entity.remote.studio.StudioEntity
import kotlinx.coroutines.flow.Flow


interface StudioRepository {

    suspend fun getStudio(conceptId: Int): Flow<PagingData<StudioEntity>>

    suspend fun getConceptName(conceptId: Int): String
}