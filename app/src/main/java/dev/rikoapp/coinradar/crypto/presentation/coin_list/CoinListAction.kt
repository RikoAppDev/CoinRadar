package dev.rikoapp.coinradar.crypto.presentation.coin_list

import dev.rikoapp.coinradar.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh : CoinListAction
}