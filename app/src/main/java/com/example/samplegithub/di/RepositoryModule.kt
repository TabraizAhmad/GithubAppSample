package com.example.samplegithub.di

import com.example.samplegithub.network.remote.GithubApiService
import com.example.samplegithub.network.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideArticleRepository(
        service: GithubApiService
    ): GithubRepository {
        return GithubRepository(service)
    }

}