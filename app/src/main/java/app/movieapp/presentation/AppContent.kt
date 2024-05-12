package app.movieapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.movieapp.ui.theme.MovieAppTheme
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import me.tatarka.inject.annotations.Inject

interface AppContent {
    @Composable
    fun Content(
        backstack:SaveableBackStack,
        navigator: Navigator,
        modifier:Modifier
    )
}

@Inject
class DefaultAppContent(
    private val circuit: Circuit
):AppContent {

    @Composable
    override fun Content(
    backstack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier
    ) {
        CircuitCompositionLocals(circuit) {
            MovieAppTheme {
                Home(
                    backStack = backstack,
                    navigator = navigator,
                    modifier = modifier
                )
            }
        }
    }
}