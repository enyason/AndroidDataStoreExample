package com.enyason.androiddatastoreexample.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.enyason.androiddatastoreexample.MainViewModel
import com.enyason.androiddatastoreexample.data.DataSource
import com.enyason.androiddatastoreexample.data.PersistentStorage
import com.enyason.androiddatastoreexample.data.Storage
import com.enyason.androiddatastoreexample.model.Config
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.enyason.androiddatastoreexample.shared.preferences")

@FlowPreview
val dataAccessModule = module {

    single<Storage<Config>> {
        PersistentStorage(
            gson = get(),
            type = object : TypeToken<List<Config>>() {}.type,
            preferenceKey = stringPreferencesKey("config"),
            dataStore = androidContext().dataStore
        )
    }

    single { Gson() }

    viewModel {
        MainViewModel(
            dataSource = DataSource(),
            configStorage = get()
        )
    }

}