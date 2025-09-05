package dev.rikoapp.coinradar.di

import dev.rikoapp.coinradar.core.data.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}