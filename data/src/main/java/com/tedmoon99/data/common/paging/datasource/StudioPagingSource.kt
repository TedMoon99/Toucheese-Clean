package com.tedmoon99.data.common.paging.datasource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tedmoon99.data.studio.datasource.StudioRemoteDataSource
import com.tedmoon99.data.studio.mapper.StudioMapper
import com.tedmoon99.data.common.paging.Paging.Companion.STARTING_PAGE
import com.tedmoon99.domain.studio.model.StudioEntity
import java.io.IOException

class StudioPagingSource(
    private val remoteDataSource: StudioRemoteDataSource,
    private val conceptId: Int,
    private val mapper: StudioMapper,
): PagingSource<Int, StudioEntity>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StudioEntity> {
        return try {

            val currentPage = params.key ?: STARTING_PAGE
            val studio = remoteDataSource.getStudio(conceptId, currentPage+1).content
            LoadResult.Page(
                data = studio.map { mapper.toDomain(it) },
                prevKey = if (currentPage == STARTING_PAGE) null else currentPage -1,
                nextKey = if (studio.isEmpty()) null else currentPage + 1,
            )

        } catch (error: IOException) {
            LoadResult.Error(error)
        } catch (error: HttpException) {
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StudioEntity>): Int? {
        return state.anchorPosition
    }
}
