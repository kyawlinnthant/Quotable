package com.kyawlinnthant.quotable.core.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    // Coroutines
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainScope

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class DefaultScope

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IoScope

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @MainScope
    fun provideMainScope(
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @DefaultScope
    fun provideDefaultScope(
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @IoScope
    fun provideIoScope(
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}