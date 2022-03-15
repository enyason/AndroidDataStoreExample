package com.enyason.androiddatastoreexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enyason.androiddatastoreexample.data.DataSource
import com.enyason.androiddatastoreexample.data.Storage
import com.enyason.androiddatastoreexample.model.Config
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

@FlowPreview
class MainViewModel(
    private val dataSource: DataSource,
    private val configStorage: Storage<Config>
) : ViewModel() {


    init {
        loadConfigs()
    }

    private fun loadConfigs() = viewModelScope.launch {
        dataSource
            .getConfigs()
            .flatMapConcat(configStorage::insert)
            .collect()
    }
}