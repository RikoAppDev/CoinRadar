package dev.rikoapp.coinradar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dev.rikoapp.coinradar.crypto.presentation.coin_list.CoinListScreenRoot
import dev.rikoapp.coinradar.crypto.presentation.coin_list.CoinListViewModel
import dev.rikoapp.coinradar.core.presentation.ui.theme.CoinRadarTheme
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rikoapp.coinradar.crypto.presentation.coin_detail.CoinDetailScreenRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinRadarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state = viewModel.state.collectAsStateWithLifecycle()

                    when {
                        state.value.selectedCoin != null -> {
                            CoinDetailScreenRoot(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = viewModel
                            )
                        }

                        else -> {
                            CoinListScreenRoot(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}