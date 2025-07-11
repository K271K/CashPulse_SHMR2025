package com.example.cashpulse

import com.example.cashpulse.di.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}