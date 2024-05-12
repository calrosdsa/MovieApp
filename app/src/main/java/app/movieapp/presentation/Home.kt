package app.movieapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.runtime.Navigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration

@Composable
internal fun Home(
    backStack: SaveableBackStack,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {

    NavigableCircuitContent(
        navigator = navigator,
        backStack = backStack,
        decoration = remember(navigator) {
            GestureNavigationDecoration(onBackInvoked = navigator::pop)
        },
        modifier = modifier.fillMaxSize(),
        )
}