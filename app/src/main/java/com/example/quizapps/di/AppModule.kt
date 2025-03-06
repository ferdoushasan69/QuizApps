package com.example.quizapps.di

import com.example.quizapps.data.remote.ApiService
import com.example.quizapps.data.repository.QuizRepositoryImpl
import com.example.quizapps.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideQuizRepository(apiService: ApiService):QuizRepository=QuizRepositoryImpl(apiService)
}