package com.enyason.androiddatastoreexample.data

import com.enyason.androiddatastoreexample.model.Config
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataSource {

    private val _configs = listOf(
        Config("in_app_billing", true),
        Config("log_in_required", false),
    )

    fun getConfigs(): Flow<List<Config>> {
        return flow {
            delay(500) // mock network delay
            emit(_configs)
        }
    }
}