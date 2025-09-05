package dev.rikoapp.coinradar.core.presentation.util

import dev.rikoapp.coinradar.R
import dev.rikoapp.coinradar.core.domain.util.NetworkError

fun NetworkError.asUiText(): UiText {
    return when (this) {

        NetworkError.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )

        NetworkError.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )

        NetworkError.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )

        NetworkError.SERVER -> UiText.StringResource(
            R.string.error_unknown
        )

        NetworkError.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        else -> UiText.StringResource(R.string.error_unknown)
    }
}