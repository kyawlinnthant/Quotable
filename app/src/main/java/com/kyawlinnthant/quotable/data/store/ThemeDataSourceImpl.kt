package com.kyawlinnthant.quotable.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ThemeDataSourceImpl
    @Inject
    constructor(
        private val ds: DataStore<Preferences>,
        @DispatcherModule.IoDispatcher private val io: CoroutineDispatcher,
    ) : ThemeDataSource {
        companion object {
            const val PREF_NAME = "ds.pref"
            val THEME = stringPreferencesKey("com.quotable.theme")
            val DYNAMIC = booleanPreferencesKey("com.quotable.dynamic")
        }

        override suspend fun saveTheme(theme: ThemeType) {
            withContext(io) {
                ds.edit {
                    it[THEME] = theme.name
                }
            }
        }

        override suspend fun retrieveTheme(): Flow<ThemeType> {
            return ds.data.catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }.map { pref ->
                pref[THEME]?.let {
                    ThemeType.valueOf(it)
                } ?: ThemeType.valueOf(ThemeType.SYSTEM.name)
            }.flowOn(io)
        }

        override suspend fun saveDynamic(enabled: Boolean) {
            withContext(io) {
                ds.edit {
                    it[DYNAMIC] = enabled
                }
            }
        }

        override suspend fun retrieveDynamic(): Flow<Boolean> {
            return ds.data.catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }.map { pref ->
                pref[DYNAMIC] != false
            }.flowOn(io)
        }
    }
