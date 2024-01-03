package com.asimkhatri.decompose.match

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

interface CreateMatchComponent {

    val groundId : StateFlow<String>

    val teamOneId: StateFlow<String>

    val teamTwoId: StateFlow<String>

    fun createMatch()

    fun selectGroundId(id: String)

    fun selectTeamOne(id: String)

    fun selectTeamTwo(id: String)
}

class DefaultCreateMatchComponent(
    componentContext: ComponentContext
) : CreateMatchComponent, KoinComponent, ComponentContext by componentContext {

    override val groundId = MutableStateFlow("")
    override val teamOneId = MutableStateFlow("")
    override val teamTwoId = MutableStateFlow("")

    override fun createMatch() {
        TODO("Not yet implemented")
    }

    override fun selectGroundId(id: String) {
        groundId.value = id
    }

    override fun selectTeamOne(id: String) {
        teamOneId.value = id
    }

    override fun selectTeamTwo(id: String) {
        teamTwoId.value = id
    }

}