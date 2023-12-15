package com.asimkhatri.domain.player

import app.cash.sqldelight.coroutines.asFlow
import com.asimkhatri.database.SharedDatabase
import com.asimkhatri.toUtcLocalTime
import com.asimkhatri.toUtcToMilliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IPlayerRepository {
    suspend fun add(player: Player)
    suspend fun remove(player: Player)
    suspend fun update(player: Player)
    suspend fun getAll(): Flow<List<Player>>
}

class PlayerRepository(private val sharedDatabase: SharedDatabase) : IPlayerRepository {

    override suspend fun add(player: Player) {
        sharedDatabase {
            it.appDatabaseQueries.addPlayer(
                player.id,
                player.name,
                player.createdAt.toUtcToMilliseconds(),
                player.updatedAt.toUtcToMilliseconds()
            )
        }
    }

    override suspend fun remove(player: Player) {
        sharedDatabase {
            it.appDatabaseQueries.deletePlayerById(player.id)
        }
    }

    override suspend fun update(player: Player) {
        sharedDatabase {
            it.appDatabaseQueries.updatePlayerById(
                player.name,
                player.updatedAt.toUtcToMilliseconds(),
                player.id
            )
        }
    }

    override suspend fun getAll(): Flow<List<Player>> = sharedDatabase { appDatabase ->
        appDatabase.appDatabaseQueries.getAllPlayers(::mapPlayer).asFlow()
            .map { it.executeAsList() }
    }

    private fun mapPlayer(
        id: String,
        name: String,
        createdAt: Long,
        updatedAt: Long,
    ): Player = Player(id, name, createdAt.toUtcLocalTime(), updatedAt.toUtcLocalTime())
}