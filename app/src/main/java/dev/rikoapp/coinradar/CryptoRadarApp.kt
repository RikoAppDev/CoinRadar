package dev.rikoapp.coinradar

import android.app.Application
import dev.rikoapp.coinradar.crypto.data.di.cryptoDataModule
import dev.rikoapp.coinradar.crypto.presentation.di.cryptoPresentationModule
import dev.rikoapp.coinradar.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoRadarApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CryptoRadarApp)
            androidLogger()

            modules(
                appModule,
                cryptoDataModule,
                cryptoPresentationModule
            )
        }
    }
}