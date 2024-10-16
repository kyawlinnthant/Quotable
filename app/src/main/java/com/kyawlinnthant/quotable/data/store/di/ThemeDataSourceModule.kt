package com.kyawlinnthant.quotable.data.store.di

import com.kyawlinnthant.quotable.data.store.ThemeDataSource
import com.kyawlinnthant.quotable.data.store.ThemeDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ThemeDataSourceModule {
    @Binds
    @Singleton
    fun bindThemeDataSource(source: ThemeDataSourceImpl): ThemeDataSource
}