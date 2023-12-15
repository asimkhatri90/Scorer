package com.asimkhatri.domain.match

import com.asimkhatri.database.SharedDatabase


interface IGroundRepository {
    suspend fun add(ground: Ground)
    suspend fun remove(ground: Ground)
    suspend fun update(ground: Ground)
    suspend fun getAll() : List<Ground>
}

class GroundRepository(private val sharedDatabase: SharedDatabase) : IGroundRepository {
    override suspend fun add(ground: Ground) {
    }

    override suspend fun remove(ground: Ground) {

    }

    override suspend fun update(ground: Ground) {

    }

    override suspend fun getAll() : List<Ground> {
        return emptyList()
    }
}