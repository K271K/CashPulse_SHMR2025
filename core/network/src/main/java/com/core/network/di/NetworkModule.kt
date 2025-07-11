package com.core.network.di

import com.core.network.RemoteDataSource
import com.core.network.RetrofitNetwork
import dagger.Module
import dagger.Provides

@Module
object NetworkModule {
    @Provides
    fun providesRemoteDataSource(retrofitNetwork: RetrofitNetwork): RemoteDataSource {
        return retrofitNetwork
    }
}