package com.enyason.androiddatastoreexample.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class PersistentStorage<T> constructor(
    private val gson: Gson,
    private val type: Type,
    private val preferenceKey: Preferences.Key<String>,
    private val dataStore: DataStore<Preferences>
) : Storage<T> {

    override fun insert(data: T): Flow<Int> {
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            cachedDataClone.add(data)
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                emit(OPERATION_SUCCESS)
            }
        }
    }

    override fun insert(data: List<T>): Flow<Int> {
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            cachedDataClone.addAll(data)
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                emit(OPERATION_SUCCESS)
            }
        }
    }

    override fun getAll(): Flow<List<T>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: EMPTY_JSON_STRING
            val elements = gson.fromJson<List<T>>(jsonString, type)
            elements
        }
    }

    override fun clearAll(): Flow<Int> {
        return flow {
            dataStore.edit {
                it.remove(preferenceKey)
                emit(OPERATION_SUCCESS)
            }
        }
    }

    override fun get(where: (T) -> Boolean): Flow<T> {
        return flow {
            val data = getAll().first().first(where)
            emit(data)
        }
    }

    companion object {
        private const val OPERATION_SUCCESS = 1
        private const val OPERATION_FAILURE = -1
        private const val EMPTY_JSON_STRING = "[]"
    }
}