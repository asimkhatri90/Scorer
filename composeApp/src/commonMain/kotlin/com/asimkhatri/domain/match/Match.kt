package com.asimkhatri.domain.match

import kotlinx.datetime.LocalDateTime

data class Match(
    val id: String,
    val teamOneId: String,
    val teamSecondId: String,
    val groundName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class Inning(
    val id: String,
    val matchId: String,
    val teamId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)