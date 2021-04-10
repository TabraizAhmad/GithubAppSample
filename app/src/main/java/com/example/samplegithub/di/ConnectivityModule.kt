package com.example.samplegithub.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.samplegithub.utlis.ConnectivityLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ConnectivityModule {

    @Provides
    @ActivityScoped
    fun provideConnectivityManager(application: Application) : ConnectivityManager {
        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    @Provides
    @ActivityScoped
    fun provideConnectivityLiveData(connectivityManager:ConnectivityManager) : ConnectivityLiveData {
        return ConnectivityLiveData(connectivityManager)
    }



}
