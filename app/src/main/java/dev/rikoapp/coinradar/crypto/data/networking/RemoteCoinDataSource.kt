package dev.rikoapp.coinradar.crypto.data.networking

import dev.rikoapp.coinradar.core.data.constructUrl
import dev.rikoapp.coinradar.core.data.safeCall
import dev.rikoapp.coinradar.core.domain.util.NetworkError
import dev.rikoapp.coinradar.core.domain.util.Result
import dev.rikoapp.coinradar.core.domain.util.map
import dev.rikoapp.coinradar.crypto.data.mappers.toCoin
import dev.rikoapp.coinradar.crypto.data.mappers.toCoinPrice
import dev.rikoapp.coinradar.crypto.data.networking.dto.CoinHistoryDto
import dev.rikoapp.coinradar.crypto.data.networking.dto.CoinsResponseDto
import dev.rikoapp.coinradar.crypto.domain.Coin
import dev.rikoapp.coinradar.crypto.domain.CoinDataSource
import dev.rikoapp.coinradar.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}