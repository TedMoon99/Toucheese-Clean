package com.toucheese.presentation

import com.tedmoon99.domain.studio.usecase.StudioUseCase.Companion.makeTruncation
import org.junit.Assert
import org.junit.Test

class CalculateTest {

    @Test
    fun truncateTest() {
        val number = 1234567 // 100만

        val expected = "1,234,567"
        val result = makeTruncation(number)

        Assert.assertEquals(expected, result)
        println("result = ${result}")

    }

}