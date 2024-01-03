package com.asimkhatri.decompose

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.asimkhatri.decompose.match.MatchesComponent
import com.asimkhatri.decompose.team.TeamsComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onMatchesTabClicked()

    fun onTeamsTabClicked()

    sealed class Child {
        class MatchesChild(val matchesComponent: MatchesComponent) : Child()
        class TeamsChild(val teamsComponent: TeamsComponent) : Child()
    }
}