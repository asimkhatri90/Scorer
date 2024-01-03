package com.asimkhatri.decompose.match

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnStart
import com.asimkhatri.decompose.DefaultRootComponent
import com.asimkhatri.decompose.RootComponent
import com.asimkhatri.decompose.asValue
import com.asimkhatri.decompose.coroutineScope
import com.asimkhatri.decompose.team.TeamsComponent
import com.asimkhatri.domain.match.Match
import com.asimkhatri.domain.match.MatchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MatchesComponent {

    val uiState: Value<MatchesUiState>

    fun onMatchClicked(id: String)

    fun onCreateMatchClicked()

    fun refreshMatches()
}

class DefaultMatchesComponent(
    componentContext: ComponentContext,
) : MatchesComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()
    private val matchRepository: MatchRepository by inject()

    private val matchesDatas = MutableStateFlow<List<Match>?>(null)
    private val isRefreshing = MutableStateFlow(false)

    private var job: Job? = null

    init {
        lifecycle.doOnStart { refresh(showLoading = uiState.value !is MatchesUiState.Success) }
    }

    private val _uiState: StateFlow<MatchesUiState> = combineUiState().stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5000),
        MatchesUiState.Loading
    )

    override val uiState: Value<MatchesUiState> = _uiState.asValue(lifecycle = lifecycle)

    override fun onMatchClicked(id: String) {
        onMatchSelected(id)
    }

    override fun onCreateMatchClicked() {
        onCreateMatchPressed()
    }

    override fun refreshMatches() {
        refresh(showLoading = true)
    }

    fun refresh(showLoading: Boolean = false, forceRefresh: Boolean = false) {
        job?.cancel()
        job = coroutineScope.launch {
            isRefreshing.value = showLoading
            matchesData(showLoading).collect {
                isRefreshing.value = false
                matchesDatas.value = it
            }
        }
    }

    private fun matchesData(showLoading: Boolean): Flow<List<Match>?> =
        flow {
            if (showLoading) {
                emit(null)
            }

            // get initial data
            coroutineScope {
                val matchResponse = async {
                    matchRepository.getAll().first()
                }
                matchResponse.await()
            }.also {
                emit(it)
            }
        }

    private fun combineUiState(): Flow<MatchesUiState> =
        matchesDatas.flatMapLatest { matchData ->
            if (matchData == null) {
                flowOf(MatchesUiState.Loading)
            } else {
                flowOf(uiStates(matchData))
            }
        }

    private fun uiStates(
        refreshData: List<Match>?,
    ): MatchesUiState {
        if (refreshData == null) {
            return MatchesUiState.Error
        }
        return MatchesUiState.Success(
            refreshData
        )
    }
}
sealed interface MatchesUiState {
    object Loading : MatchesUiState

    object Error : MatchesUiState

    data class Success(val matches: List<Match>) : MatchesUiState
}