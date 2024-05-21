package com.example.common.date

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

object DateTimeConvert {
    fun getStartOfWeek(today: LocalDate): LocalDate =
        today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    fun getEndOfWeek(today: LocalDate): LocalDate =
        today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

    fun getStartOfMonth(date: LocalDate): LocalDate =
        date.with(TemporalAdjusters.firstDayOfMonth())

    fun getEndOfMonth(date: LocalDate): LocalDate =
        date.with(TemporalAdjusters.lastDayOfMonth())
}
