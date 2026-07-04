package com.demo.mcqquiz.di

import com.demo.mcqquiz.data.datasource.local.QuestionLocalDataSource
import com.demo.mcqquiz.data.datasource.local.QuestionLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindQuestionLocalDataSource(
        impl: QuestionLocalDataSourceImpl
    ): QuestionLocalDataSource
}