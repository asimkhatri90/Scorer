package com.asimkhatri.di

import com.asimkhatri.database.SharedDatabase
import com.asimkhatri.domain.match.GroundRepository
import com.asimkhatri.domain.match.IGroundRepository
import com.asimkhatri.domain.match.IMatchRepository
import com.asimkhatri.domain.match.MatchRepository
import com.asimkhatri.domain.player.IPlayerRepository
import com.asimkhatri.domain.player.PlayerRepository
import com.asimkhatri.domain.team.ITeamRepository
import com.asimkhatri.domain.team.TeamRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            sqlDelightModule,
            platformModule()
        )
    }

val repositoryModule = module {
    single<IGroundRepository> { GroundRepository(get()) }
    single<IMatchRepository> { MatchRepository(get()) }
    single<ITeamRepository> { TeamRepository(get()) }
    single<IPlayerRepository> { PlayerRepository(get()) }
}

val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

fun initKoin() = initKoin {}