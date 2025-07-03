package com.core.network.di

import com.core.network.RemoteDataSource
import com.core.network.RetrofitNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {
    @Provides
    fun providesRemoteDataSource(retrofitNetwork: RetrofitNetwork): RemoteDataSource {
        return retrofitNetwork
    }
}