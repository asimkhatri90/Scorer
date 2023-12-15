package com.asimkhatri.domain.match

import app.cash.sqldelight.coroutines.asFlow
import com.asimkhatri.database.SharedDatabase
import com.asimkhatri.toUtcLocalTime
import com.asimkhatri.toUtcToMilliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IMatchRepository {
    suspend fun add(match: Match)
    suspend fun remove(match: Match)
    suspend fun update(match: Match)
    suspend fun getAll(): Flow<List<Match>>
}

class MatchRepository(private val sharedDatabase: SharedDatabase) : IMatchRepository {

    override suspend fun add(match: Match) {
        sharedDatabase {
            it.appDatabaseQueries.addMatch(
                match.id,
                match.teamOneId,
                match.teamSecondId,
                match.status.ordinal.toLong(),
                match.matchSettingId,
                match.groundId,
                match.createdAt.toUtcToMilliseconds(),
                match.updatedAt.toUtcToMilliseconds()
            )
        }
    }

    override suspend fun remove(match: Match) {
        sharedDatabase {
            it.appDatabaseQueries.deleteMatchById(match.id)
        }
    }

    override suspend fun update(match: Match) {
    }

    override suspend fun getAll(): Flow<List<Match>> =
        sharedDatabase { appDatabase ->
            appDatabase.appDatabaseQueries.getAllMatches(::mapMatch).asFlow()
                .map { it.executeAsList() }
        }

    private fun mapMatch(
        id: String,
        teamOneId: String,
        teamTwoId: String,
        status: Long,
        settingsId: String,
        groundId: String,
        createdAt: Long,
        updatedAt: Long,
    ): Match = Match(
        id,
        teamOneId,
        teamTwoId,
        MatchStatus.entries[status.toInt()],
        settingsId,
        groundId,
        createdAt.toUtcLocalTime(),
        updatedAt.toUtcLocalTime()
    )
}