package app.movieapp.presentation.ui.discover

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import app.movieapp.data.model.dto.movies.MovieDto
import app.movieapp.data.repository.discover.PopularDataSource
import app.movieapp.presentation.screens.DiscoverScreen
import app.movieapp.presentation.screens.ShowScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
class DiscoverUiPresenterFactory(
    private val presenterFactory: (Navigator) -> DiscoverPresenter,
):Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ):Presenter<*>? {
        return when (screen) {
            is DiscoverScreen -> presenterFactory(navigator)
            else -> null
        }
    }
}

@Inject
class DiscoverPresenter(
    @Assisted private val navigator: Navigator,
    private val popularDataSource: PopularDataSource
): Presenter<DiscoverUiState> {
    @Composable
    override fun present(): DiscoverUiState {
        val scope = rememberCoroutineScope()
        val trendingLoading = rememberSaveable {
            mutableStateOf(false)
        }
        val popularLoading = rememberSaveable {
            mutableStateOf(false)
        }
        val upcomingLoading = rememberSaveable {
            mutableStateOf(false)
        }
        val topRatedLoading = rememberSaveable {
            mutableStateOf(false)
        }
        val nowPlayingLoading = rememberSaveable {
            mutableStateOf(false)
        }
        val popularMovies = rememberSaveable {
            mutableStateOf(emptyList<MovieDto>())
        }
        val topRatedMovies = rememberSaveable {
            mutableStateOf(emptyList<MovieDto>())
        }
        val upcomingMovies = rememberSaveable {
            mutableStateOf(emptyList<MovieDto>())
        }
        val nowPlaying = rememberSaveable {
            mutableStateOf(emptyList<MovieDto>())
        }

        fun loadData() {
            scope.launch {
                try {
                    popularLoading.value=true
                    val res = popularDataSource.getPopularMovies(1)
                    popularMovies.value = res.results
                    popularLoading.value=false
                } catch (e: Exception) {
                    popularLoading.value=false
                    Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
                }
            }
            scope.launch {
                try {
                    upcomingLoading.value = true
                    val res = popularDataSource.getUpcomingMovies(1)
                    upcomingMovies.value = res.results
                    upcomingLoading.value = false
                } catch (e: Exception) {
                    upcomingLoading.value = false
                    Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
                }
            }
            scope.launch {
                try {
                    topRatedLoading.value = true
                    val res = popularDataSource.getTopRatedMovies(1)
                    topRatedMovies.value = res.results
                    topRatedLoading.value = false
                } catch (e: Exception) {
                    topRatedLoading.value = false
                    Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
                }
            }
            scope.launch {
                try {
                    nowPlayingLoading.value=true
                    val res = popularDataSource.getNowPlayingMovies(1)
                    nowPlaying.value = res.results
                    nowPlayingLoading.value=false
                } catch (e: Exception) {
                    nowPlayingLoading.value=false
                    Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
                }
            }
        }

        fun eventSink(event:DiscoverUiEvent){
            when (event) {
                is DiscoverUiEvent.Refresh->{
                    loadData()
                }

                is DiscoverUiEvent.OpenShowDetails -> {
                    navigator.goTo(ShowScreen(event.showId))

                }
            }
        }

        LaunchedEffect(Unit) {
            loadData()
        }



        return DiscoverUiState(
            upcomingRefreshing = upcomingLoading.value,
            topRatedRefreshing = topRatedLoading.value,
            popularRefreshing = popularLoading.value,
            nowPlayingRefreshing = nowPlayingLoading.value,
            eventSink = ::eventSink,
            popularMovies = popularMovies.value,
            nowPlayingMovies = nowPlaying.value,
            topRatedMovies = topRatedMovies.value,
            upcomingMovies = upcomingMovies.value
        )

    }

}