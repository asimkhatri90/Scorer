package com.asimkhatri.domain.player

import com.asimkhatri.database.SharedDatabase

interface IPlayerRepository {
    suspend fun add(player: Player)
    suspend fun remove(player: Player)
    suspend fun update(player: Player)
    suspend fun getAll() : List<Player>
}

class PlayerRepository(private val sharedDatabase: SharedDatabase) : IPlayerRepository {

    override suspend fun add(player: Player) {

    }

    override suspend fun remove(player: Player) {

    }

    override suspend fun update(player: Player) {

    }

    override suspend fun getAll() : List<Player> {
        return emptyList()
    }
}