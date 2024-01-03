package com.asimkhatri.decompose.match

import kotlinx.coroutines.flow.StateFlow

interface MatchDetailsComponent {

    val data: StateFlow<Model>

    fun selectNoOfOvers(overs: Int)

    fun tossWonBy(teamId: String)

    fun battingFirst(teamId: String)

    fun battingSecond(teamId: String)

    data class Model(
        val noOfOvers: Int,
        val tossWonByTeamId: String,
        val battingFirstTeamId: String,
        val battingSecondTeamId: String
    )
}