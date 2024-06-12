package com.dicoding.warnaku_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val loginToken = stringPreferencesKey("token")
    private val loginStatus = booleanPreferencesKey("status")

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[loginToken] = token
        }
    }

    suspend fun loginPref() {
        dataStore.edit { preferences ->
            preferences[loginStatus] = true
        }
    }

    fun getLoginStatus(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[loginStatus]
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[loginToken]
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[loginStatus] = false
            preferences[loginToken] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}