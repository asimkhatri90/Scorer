package com.asimkhatri.domain.match

import kotlinx.datetime.LocalDateTime

enum class MatchStatus {
    YetToStart,
    OnGoing,
    Abandoned,
    Finished
}


data class Match(
    val id: String,
    val teamOneId: String,
    val teamSecondId: String,
    val status: MatchStatus,
    val groundId: String,
    val matchSettingId: String,
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

data class MatchSettings(val id: String)