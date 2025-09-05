package dev.rikoapp.coinradar.crypto.data.networking

import dev.rikoapp.coinradar.core.data.constructUrl
import dev.rikoapp.coinradar.core.data.safeCall
import dev.rikoapp.coinradar.core.domain.util.NetworkError
import dev.rikoapp.coinradar.core.domain.util.Result
import dev.rikoapp.coinradar.core.domain.util.map
import dev.rikoapp.coinradar.crypto.data.mappers.toCoin
import dev.rikoapp.coinradar.crypto.data.networking.dto.CoinsResponseDto
import dev.rikoapp.coinradar.crypto.domain.Coin
import dev.rikoapp.coinradar.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}