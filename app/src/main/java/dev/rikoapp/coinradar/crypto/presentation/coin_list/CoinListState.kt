package dev.rikoapp.coinradar.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import dev.rikoapp.coinradar.crypto.presentation.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)