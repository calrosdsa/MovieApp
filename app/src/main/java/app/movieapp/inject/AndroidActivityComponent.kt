package app.movieapp.inject

import android.app.Activity
import androidx.core.os.ConfigurationCompat
import app.movieapp.presentation.AppContent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import java.util.Locale

@ActivityScope
@Component
abstract class AndroidActivityComponent(
  @get:Provides override val activity: Activity,
  @Component val applicationComponent: AndroidApplicationComponent,
) : ActivityComponent,ProdUiComponent {
  abstract val appContent: AppContent

  companion object
}

interface ActivityComponent {
  @get:Provides
  val activity: Activity

  @Provides
  fun provideActivityLocale(activity: Activity): Locale {
    return ConfigurationCompat.getLocales(activity.resources.configuration)
      .get(0) ?: Locale.getDefault()
  }
}