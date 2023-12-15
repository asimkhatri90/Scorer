package com.asimkhatri.domain.team

import com.asimkhatri.database.SharedDatabase

interface ITeamRepository {
    suspend fun add(team: Team)
    suspend fun remove(team: Team)
    suspend fun update(team: Team)
    suspend fun getAll() : List<Team>
}

class TeamRepository(private val sharedDatabase: SharedDatabase) : ITeamRepository {

    override suspend fun add(team: Team) {

    }

    override suspend fun remove(team: Team) {

    }

    override suspend fun update(team: Team) {

    }

    override suspend fun getAll() : List<Team> {
        return emptyList()
    }
}