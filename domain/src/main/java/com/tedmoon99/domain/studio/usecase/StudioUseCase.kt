package com.tedmoon99.domain.studio.usecase

import androidx.paging.PagingData
import com.tedmoon99.domain.studio.model.StudioEntity
import com.tedmoon99.domain.studio.repository.StudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudioUseCase @Inject constructor(
    private val repository: StudioRepository
) {

    suspend fun getStudio(conceptId: Int): Flow<PagingData<StudioEntity>> {
        return repository.getStudio(conceptId)
    }

    suspend fun getConceptName(conceptId: Int): String = repository.getConceptName(conceptId)


    companion object {
        // 계산 처리 로직
        fun makeTruncation(number: Int): String {
            val result = StringBuilder()
            var num = number

            while (num >= 1000) {
                val temp = num % 1000
                result.insert(0, ",${temp.toString().padStart(3, '0')}")
                num /= 1000
            }
            result.insert(0, num)
            return result.toString()
        }
    }
}