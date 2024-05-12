package app.movieapp.presentation

import app.movieapp.inject.ActivityScope
import me.tatarka.inject.annotations.Provides

interface RootUiComponent {
  @Provides
  @ActivityScope
  fun bindTiviContent(impl: DefaultAppContent): AppContent = impl
}