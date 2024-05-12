// Copyright 2023, Google LLC, Christopher Banes and the Tivi project contributors
// SPDX-License-Identifier: Apache-2.0

package app.movieapp.inject

import android.app.Application
import app.movieapp.data.model.AppCoroutineDispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
  @get:Provides val application: Application,
) : ApplicationComponent {

  abstract val dispatchers: AppCoroutineDispatchers

  /**
   * We have no interceptors in the standard release currently
   */

  companion object
}
