package app.movieapp.presentation.ui.show

import androidx.compose.runtime.Immutable
import app.movieapp.data.model.dto.movieDetail.MovieDetailDto
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class ShowUiState(
    val loading: Boolean = false,
    val showId: Long = 0,
    val showDetail: MovieDetailDto? = null,
    val eventSink: (ShowUiEvent) -> Unit,
):CircuitUiState {
    val refreshing: Boolean
        get() = loading
}

sealed interface ShowUiEvent : CircuitUiEvent {
    data class Refresh(val fromUser: Boolean = false) : ShowUiEvent
    data object NavigateUp:ShowUiEvent
//    data class ClearMessage(val id: Long) : ShowUiEvent
//    object OpenAccount : ShowUiEvent
//    object OpenPopularShows : ShowUiEvent
//    object OpenRecommendedShows : ShowUiEvent
//    object OpenTrendingShows : ShowUiEvent
    data class OpenShowDetails(val showId: Long) : ShowUiEvent
}
