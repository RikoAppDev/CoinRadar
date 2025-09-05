package dev.rikoapp.coinradar.crypto.domain

import dev.rikoapp.coinradar.core.domain.util.NetworkError
import dev.rikoapp.coinradar.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}