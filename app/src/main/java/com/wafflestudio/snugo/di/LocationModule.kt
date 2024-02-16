package com.wafflestudio.snugo.di

import android.content.Context
import com.wafflestudio.snugo.location.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object LocationModule {
    @Provides
    fun provideLocationProvider(
        @ActivityContext context: Context,
    ): LocationProvider {
        return LocationProvider(context)
    }
}
