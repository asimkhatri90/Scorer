package com.asimkhatri.domain.team

import app.cash.sqldelight.coroutines.asFlow
import com.asimkhatri.database.SharedDatabase
import com.asimkhatri.toUtcLocalTime
import com.asimkhatri.toUtcToMilliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ITeamRepository {
    suspend fun add(team: Team)
    suspend fun remove(team: Team)
    suspend fun update(team: Team)
    suspend fun getAll(): Flow<List<Team>>
}

class TeamRepository(private val sharedDatabase: SharedDatabase) : ITeamRepository {

    override suspend fun add(team: Team) {
        sharedDatabase {
            it.appDatabaseQueries.addTeam(
                team.id,
                team.name,
                team.createdAt.toUtcToMilliseconds(),
                team.updatedAt.toUtcToMilliseconds()
            )
        }
    }

    override suspend fun remove(team: Team) {
        sharedDatabase {
            it.appDatabaseQueries.deleteTeamById(team.id)
        }
    }

    override suspend fun update(team: Team) {
        sharedDatabase {
            it.appDatabaseQueries.updateTeamById(
                team.name,
                team.updatedAt.toUtcToMilliseconds(),
                team.id
            )
        }
    }

    override suspend fun getAll(): Flow<List<Team>> = sharedDatabase { appDatabase ->
        appDatabase.appDatabaseQueries.gettAllTeams(::mapTeam).asFlow().map { it.executeAsList() }
    }

    private fun mapTeam(
        id: String,
        name: String,
        createdAt: Long,
        updatedAt: Long,
    ): Team = Team(id, name, createdAt.toUtcLocalTime(), updatedAt.toUtcLocalTime())
}