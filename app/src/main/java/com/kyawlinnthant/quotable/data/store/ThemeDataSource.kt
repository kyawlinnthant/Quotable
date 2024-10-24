package com.kyawlinnthant.quotable.data.store

import kotlinx.coroutines.flow.Flow

interface ThemeDataSource {
    suspend fun saveTheme(theme: ThemeType)

    suspend fun retrieveTheme(): Flow<ThemeType>

    suspend fun saveDynamic(enabled: Boolean)

    suspend fun retrieveDynamic(): Flow<Boolean>
}
