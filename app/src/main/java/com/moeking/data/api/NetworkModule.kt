package com.moeking.data.api

import com.moeking.data.api.RetrofitClient
import org.koin.dsl.module

/**
 * 网络模块
 */
val networkModule = module {

    // Character API
    single { RetrofitClient.createService(CharacterApi::class.java) }
}
