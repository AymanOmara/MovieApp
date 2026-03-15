package com.example.domain.model

import com.example.domain.entity.MonthRangeGenerator
import org.junit.Assert.assertEquals
import org.junit.Test

class MonthRangeGeneratorTest {

    @Test
    fun generateMonthRanges_returns12Months() {
        val result = MonthRangeGenerator.generateMonthRanges(2024)
        assertEquals(12, result.size)
    }

    @Test
    fun generateMonthRanges_labelsContainYear() {
        val result = MonthRangeGenerator.generateMonthRanges(2024)
        result.forEach { range ->
            assertEquals("${range.monthLabel} should end with 2024", "2024", range.monthLabel.takeLast(4))
        }
    }

    @Test
    fun generateMonthRanges_januaryHasCorrectDates() {
        val result = MonthRangeGenerator.generateMonthRanges(2024)
        val january = result.first()
        assertEquals("January 2024", january.monthLabel)
        assertEquals("2024-01-01", january.startDate)
        assertEquals("2024-01-31", january.endDate)
    }

    @Test
    fun generateMonthRanges_decemberHasCorrectDates() {
        val result = MonthRangeGenerator.generateMonthRanges(2024)
        val december = result.last()
        assertEquals("December 2024", december.monthLabel)
        assertEquals("2024-12-01", december.startDate)
        assertEquals("2024-12-31", december.endDate)
    }

    @Test
    fun generateMonthRanges_februaryLeapYear() {
        val result = MonthRangeGenerator.generateMonthRanges(2024)
        val february = result[1]
        assertEquals("February 2024", february.monthLabel)
        assertEquals("2024-02-01", february.startDate)
        assertEquals("2024-02-29", february.endDate)
    }

    @Test
    fun generateMonthRanges_allMonthsHaveConsecutiveLabels() {
        val expectedLabels = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val result = MonthRangeGenerator.generateMonthRanges(2020)
        result.forEachIndexed { index, range ->
            assertEquals("${expectedLabels[index]} 2020", range.monthLabel)
        }
    }
}
