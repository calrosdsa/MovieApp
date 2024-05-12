package app.movieapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import app.movieapp.inject.AndroidActivityComponent
import app.movieapp.inject.AndroidApplicationComponent
import app.movieapp.inject.create
import app.movieapp.presentation.screens.DiscoverScreen
import app.movieapp.ui.theme.MovieAppTheme
import app.moviebase.tmdb.Tmdb3
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationComponent = AndroidApplicationComponent.from(this)
        val component = AndroidActivityComponent::class.create(this, applicationComponent)
        enableEdgeToEdge()
        lifecycleScope.launch {
        setContent {
            val backstack = rememberSaveableBackStack(listOf(DiscoverScreen))
            val navigator = rememberCircuitNavigator(backstack)
            component.appContent.Content(
                backstack = backstack,
                navigator = navigator,
                modifier = Modifier
            )
        }
    }
}
    }


private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent {
    return (context.applicationContext as AppApplication).component
}
