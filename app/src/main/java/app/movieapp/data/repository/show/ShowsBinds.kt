package app.movieapp.data.repository.show

import app.movieapp.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

interface ShowsBinds {
  @ApplicationScope
  @Provides
  fun bindTmdbShowDataSource(bind: ShowDataSourceImpl): ShowDataSource = bind
}