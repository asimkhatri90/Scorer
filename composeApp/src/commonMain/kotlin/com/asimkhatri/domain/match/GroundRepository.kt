package com.asimkhatri.domain.match

import app.cash.sqldelight.coroutines.asFlow
import com.asimkhatri.database.SharedDatabase
import com.asimkhatri.toUtcLocalTime
import com.asimkhatri.toUtcToMilliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface IGroundRepository {
    suspend fun add(ground: Ground)
    suspend fun remove(ground: Ground)
    suspend fun update(ground: Ground)
    suspend fun getAll(): Flow<List<Ground>>
}

class GroundRepository(private val sharedDatabase: SharedDatabase) : IGroundRepository {
    override suspend fun add(ground: Ground) {
        sharedDatabase {
            it.appDatabaseQueries.addGround(
                ground.id,
                ground.name,
                ground.createdAt.toUtcToMilliseconds(),
                ground.updatedAt.toUtcToMilliseconds()
            )
        }
    }

    override suspend fun remove(ground: Ground) {
        sharedDatabase {
            it.appDatabaseQueries.deleteGroundById(ground.id)
        }
    }

    override suspend fun update(ground: Ground) {
        sharedDatabase {
            it.appDatabaseQueries.updateGroundById(
                ground.name,
                ground.updatedAt.toUtcToMilliseconds(),
                ground.id
            )
        }
    }

    override suspend fun getAll(): Flow<List<Ground>> =
        sharedDatabase { appDatabase ->
            appDatabase.appDatabaseQueries.getAllGrounds(::mapGround).asFlow()
                .map { it.executeAsList() }
        }

    private fun mapGround(
        id: String,
        name: String,
        createdAt: Long,
        updatedAt: Long,
    ): Ground = Ground(id, name, createdAt.toUtcLocalTime(), updatedAt.toUtcLocalTime())
}