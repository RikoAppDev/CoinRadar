package dev.rikoapp.coinradar.crypto.presentation.coin_list

import dev.rikoapp.coinradar.core.presentation.util.UiText

sealed interface CoinListEvent {
    data class Error(val message: UiText) : CoinListEvent
}