package app.movieapp.presentation.ui.show

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import app.movieapp.data.model.dto.movieDetail.MovieDetailDto
import app.movieapp.data.repository.show.ShowDataSourceImpl
import app.movieapp.presentation.screens.ShowScreen
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
class ShowUiPresenterFactory(
    private val presenterFactory: (ShowScreen,Navigator) -> ShowPresenter,
):Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ):Presenter<*>? {
        return when (screen) {
            is ShowScreen -> presenterFactory(screen,navigator)
            else -> null
        }
    }
}

@Inject
class ShowPresenter(
    @Assisted private val screen: ShowScreen,
    @Assisted private val navigator: Navigator,
    private val showDataSourceImpl: ShowDataSourceImpl
): Presenter<ShowUiState> {
    private val showId: Long get() = screen.id
    @Composable
    override fun present(): ShowUiState {
        val scope = rememberCoroutineScope()
        val loading = rememberSaveable {
            mutableStateOf(true)
        }
        val showDetail = remember {
            mutableStateOf<MovieDetailDto?>(null)
        }

        LaunchedEffect(Unit) {
            try {
                loading.value  = true
                val res = showDataSourceImpl.getShow(showId)
                Log.d("DEBUG_APP_ERROR", res.toString())
                showDetail.value = res
                loading.value  = false
            } catch (e: Exception) {
                loading.value  = false
                Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
            }
        }


        fun eventSink(event: ShowUiEvent) {
            when (event) {
                is ShowUiEvent.Refresh -> {
                    scope.launch {
                        try {
                            loading.value  = true
                            val res = showDataSourceImpl.getShow(showId)
                            Log.d("DEBUG_APP_ERROR", res.toString())
                            showDetail.value = res
                            loading.value  = false
                        } catch (e: Exception) {
                            loading.value  = false
                            Log.d("DEBUG_APP_ERROR", e.localizedMessage ?: "")
                        }
                    }
                }

                is ShowUiEvent.OpenShowDetails -> {
                    navigator.goTo(ShowScreen(event.showId))
                }
                is ShowUiEvent.NavigateUp -> navigator.pop()
            }
        }

        return ShowUiState(
            loading = loading.value,
            eventSink = ::eventSink,
            showId = showId,
            showDetail = showDetail.value
        )

    }

}