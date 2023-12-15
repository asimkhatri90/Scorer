package com.asimkhatri

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toUtcToMilliseconds() : Long {
    return this.toInstant(TimeZone.UTC).toEpochMilliseconds()
}

fun Long.toUtcLocalTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
}