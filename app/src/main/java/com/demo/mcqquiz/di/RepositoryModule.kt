package com.demo.mcqquiz.di

import com.demo.mcqquiz.data.repository.QuestionRepositoryImpl
import com.demo.mcqquiz.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuestionRepository(
        impl: QuestionRepositoryImpl
    ): QuestionRepository
}