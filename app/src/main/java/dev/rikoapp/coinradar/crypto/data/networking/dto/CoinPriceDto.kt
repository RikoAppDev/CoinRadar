package dev.rikoapp.coinradar.crypto.data.networking.dto

import dev.rikoapp.coinradar.crypto.domain.CoinPrice
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}