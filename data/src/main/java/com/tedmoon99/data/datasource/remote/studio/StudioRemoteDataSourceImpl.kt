package com.tedmoon99.data.datasource.remote.studio

import com.tedmoon99.data.datasource.remote.studio.api.StudioService
import com.tedmoon99.data.model.remote.concept.studios.paging.StudioDto


class StudioRemoteDataSourceImpl(
    private val api: StudioService,
) : StudioRemoteDataSource {
    override suspend fun getStudio(conceptId: Int, page: Int): StudioDto {
        return api.getStudio(conceptId, page)
    }

}