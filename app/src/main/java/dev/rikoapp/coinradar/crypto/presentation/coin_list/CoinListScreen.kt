@file:OptIn(ExperimentalMaterial3Api::class)

package dev.rikoapp.coinradar.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.rikoapp.coinradar.core.presentation.ui.theme.CoinRadarTheme
import dev.rikoapp.coinradar.crypto.presentation.coin_list.components.CoinListItem
import dev.rikoapp.coinradar.crypto.presentation.coin_list.components.previewCoin

@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onAction(CoinListAction.OnRefresh) }
        ) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.coins) { coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun CoinListScreenPreview() {
    CoinRadarTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..10).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            onAction = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}