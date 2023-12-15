package com.asimkhatri.domain.team

import kotlinx.datetime.LocalDateTime

data class Team(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
