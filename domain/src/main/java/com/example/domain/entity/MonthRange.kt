package com.example.domain.entity

import java.time.LocalDate

data class MonthRange(
    val monthLabel: String,
    val startDate: String,
    val endDate: String
)

object MonthRangeGenerator {
    
    private val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun generateMonthRanges(year: Int): List<MonthRange> {
        return (1..12).map { month ->
            val startDate = LocalDate.of(year, month, 1)
            val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())

            MonthRange(
                monthLabel = "${monthNames[month - 1]} $year",
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )
        }
    }
}
