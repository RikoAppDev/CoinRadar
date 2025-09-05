package dev.rikoapp.coinradar.crypto.data.di

import dev.rikoapp.coinradar.crypto.data.networking.RemoteCoinDataSource
import dev.rikoapp.coinradar.crypto.domain.CoinDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cryptoDataModule = module {
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
}