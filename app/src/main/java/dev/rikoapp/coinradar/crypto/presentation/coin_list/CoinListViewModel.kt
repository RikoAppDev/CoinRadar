package dev.rikoapp.coinradar.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rikoapp.coinradar.core.domain.util.onError
import dev.rikoapp.coinradar.core.domain.util.onSuccess
import dev.rikoapp.coinradar.core.presentation.util.UiText
import dev.rikoapp.coinradar.core.presentation.util.asUiText
import dev.rikoapp.coinradar.crypto.domain.CoinDataSource
import dev.rikoapp.coinradar.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadCoins()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CoinListState()
        )

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                _state.update {
                    it.copy(selectedCoin = action.coinUi)
                }
            }

            CoinListAction.OnRefresh -> {
                loadCoins(isRefresh = true)
            }
        }
    }

    private fun loadCoins(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                _state.update { it.copy(isLoading = true) }
            } else {
                _state.update { it.copy(isRefreshing = true) }
            }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            coins = coins.map { coin -> coin.toCoinUi() },
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                    _events.send(CoinListEvent.Error(error.asUiText()))
                }
        }
    }
}