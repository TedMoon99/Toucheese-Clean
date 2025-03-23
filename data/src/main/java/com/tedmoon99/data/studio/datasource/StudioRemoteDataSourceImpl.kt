package com.tedmoon99.data.studio.datasource

import com.tedmoon99.data.studio.model.StudioDto


class StudioRemoteDataSourceImpl(
    private val api: StudioService,
) : StudioRemoteDataSource {
    override suspend fun getStudio(conceptId: Int, page: Int): StudioDto {
        return api.getStudio(conceptId, page)
    }

    override suspend fun getConceptName(conceptId: Int): String {
        val conceptList = api.getConcept()
        return conceptList.first { it.id == conceptId }.name.replace("느낌","").trim()
    }

}