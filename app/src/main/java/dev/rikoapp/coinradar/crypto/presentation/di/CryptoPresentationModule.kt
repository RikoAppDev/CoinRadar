package dev.rikoapp.coinradar.crypto.presentation.di

import dev.rikoapp.coinradar.crypto.presentation.coin_list.CoinListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cryptoPresentationModule = module {
    viewModelOf(::CoinListViewModel)
}