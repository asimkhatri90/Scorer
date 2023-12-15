package com.asimkhatri.domain.match

import com.asimkhatri.database.SharedDatabase

interface IMatchRepository {
    suspend fun add(match: Match)
    suspend fun remove(match: Match)
    suspend fun update(match: Match)
    suspend fun getAll() : List<Match>
}

class MatchRepository(private val sharedDatabase: SharedDatabase) : IMatchRepository {

    override suspend fun add(match: Match) {

    }

    override suspend fun remove(match: Match) {

    }

    override suspend fun update(match: Match) {

    }

    override suspend fun getAll() : List<Match> {
        return emptyList()
    }
}