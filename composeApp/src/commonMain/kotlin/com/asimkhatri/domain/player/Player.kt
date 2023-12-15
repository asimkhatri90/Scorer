package com.asimkhatri.domain.player

import kotlinx.datetime.LocalDateTime

data class Player(
    val id: String,
    val name: String,
    val teamId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
