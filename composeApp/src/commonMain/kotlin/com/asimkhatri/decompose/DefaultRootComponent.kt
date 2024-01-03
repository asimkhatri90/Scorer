package com.asimkhatri.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.asimkhatri.decompose.match.DefaultMatchesComponent
import com.asimkhatri.decompose.match.MatchesComponent
import com.asimkhatri.decompose.team.TeamsComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigationStack = StackNavigation<Config>()

    private val stack = childStack(
        source = navigationStack,
        serializer = Config.serializer(),
        initialStack = { getInitialStack() },
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    private fun createChild(
        configuration: Config, context: ComponentContext
    ): RootComponent.Child {
        return when (configuration) {
            Config.Matches -> RootComponent.Child.MatchesChild(DefaultMatchesComponent(context))
            is Config.Teams -> RootComponent.Child.TeamsChild(TeamsComponent(context))
        }
    }

    override fun onMatchesTabClicked() {
        navigationStack.bringToFront(Config.Matches)
    }

    override fun onTeamsTabClicked() {
        navigationStack.bringToFront(Config.Teams)
    }

    private companion object {
        fun getInitialStack(): List<Config> {
            return listOf(Config.Matches)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Matches : Config

        @Serializable
        data object Teams : Config
    }
}