package com.example.domain.entity

import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

data class MonthRange(
    val monthLabel: String,
    val startDate: String,
    val endDate: String
)

object MonthRangeGenerator {

    fun generateMonthRanges(year: Int): List<MonthRange> {
        return Month.entries.map { month ->
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())

            MonthRange(
                monthLabel = "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year",
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )
        }
    }
}
