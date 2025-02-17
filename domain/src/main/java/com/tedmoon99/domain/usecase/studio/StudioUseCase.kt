package com.tedmoon99.domain.usecase.studio

import androidx.paging.PagingData
import com.tedmoon99.domain.entity.remote.concept.studios.StudioEntity
import com.tedmoon99.domain.repository.studio.StudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioUseCase @Inject constructor(
    private val repository: StudioRepository
) {

    suspend fun getStudio(conceptId: Int): Flow<PagingData<StudioEntity>> {
        return repository.getStudio(conceptId)
    }

}