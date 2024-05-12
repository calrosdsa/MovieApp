package app.movieapp.presentation.ui.discover

import androidx.compose.runtime.Immutable
import app.movieapp.data.model.dto.movies.MovieDto
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class DiscoverUiState(
    val upcomingRefreshing: Boolean = false,
    val popularRefreshing: Boolean = false,
    val topRatedRefreshing: Boolean = false,
    val nowPlayingRefreshing: Boolean = false,
    val popularMovies:List<MovieDto> = emptyList(),
    val nowPlayingMovies:List<MovieDto> = emptyList(),
    val topRatedMovies:List<MovieDto> = emptyList(),
    val upcomingMovies:List<MovieDto> = emptyList(),

    val eventSink: (DiscoverUiEvent) -> Unit,
):CircuitUiState {
    val refreshing: Boolean
        get() = upcomingRefreshing || popularRefreshing || topRatedRefreshing || nowPlayingRefreshing
}

sealed interface DiscoverUiEvent : CircuitUiEvent {
    data class Refresh(val fromUser: Boolean = false) : DiscoverUiEvent
//    data class ClearMessage(val id: Long) : DiscoverUiEvent
//    object OpenAccount : DiscoverUiEvent
//    object OpenPopularShows : DiscoverUiEvent
//    object OpenRecommendedShows : DiscoverUiEvent
//    object OpenTrendingShows : DiscoverUiEvent
    data class OpenShowDetails(val showId: Long) : DiscoverUiEvent
}
