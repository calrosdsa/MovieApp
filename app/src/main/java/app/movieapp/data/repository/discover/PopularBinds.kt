package app.movieapp.data.repository.discover

import app.movieapp.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

interface PopularBinds {
  @ApplicationScope
  @Provides
  fun bindTmdbShowDataSource(bind: PupularDataSourceImpl): PopularDataSource = bind
}