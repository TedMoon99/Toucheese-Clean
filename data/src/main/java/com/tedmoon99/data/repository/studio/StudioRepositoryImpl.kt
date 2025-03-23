package com.tedmoon99.data.repository.studio

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tedmoon99.data.datasource.remote.studio.StudioRemoteDataSource
import com.tedmoon99.data.mapper.studio.StudioMapper
import com.tedmoon99.data.repository.studio.paging.StudioPagingSource
import com.tedmoon99.domain.studio.model.StudioEntity
import com.tedmoon99.domain.studio.repository.StudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudioRemoteDataSource,
    private val mapper: StudioMapper,
) : StudioRepository {

    // 컨셉 스튜디오 목록 조회 (paging)
    override suspend fun getStudio(
        conceptId: Int,
    ): Flow<PagingData<StudioEntity>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false),
            pagingSourceFactory = {
                StudioPagingSource(remoteDataSource, conceptId, mapper)
            }
        ).flow
    }

    // 컨셉 정보 조회
    override suspend fun getConceptName(conceptId: Int): String {
        return remoteDataSource.getConceptName(conceptId)
    }

}