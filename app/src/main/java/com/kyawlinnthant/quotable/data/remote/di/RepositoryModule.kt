package com.kyawlinnthant.quotable.data.remote.di

import com.kyawlinnthant.quotable.data.remote.repo.QuoteRepositoryImpl
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository
}