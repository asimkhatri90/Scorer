package com.asimkhatri.domain.match

import kotlinx.datetime.LocalDateTime

data class Ground(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)