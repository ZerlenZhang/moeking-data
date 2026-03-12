package com.moeking.data.di

import android.content.Context
import androidx.room.Room
import com.moeking.data.models.Character
import com.moeking.data.models.Team
import com.moeking.data.utils.AppDatabase
import com.moeking.data.utils.CharacterDao
import com.moeking.data.utils.CharacterRepository
import com.moeking.data.utils.TeamDao
import com.moeking.data.utils.TeamRepository
import com.moeking.data.utils.CharacterViewModel
import com.moeking.data.utils.TeamViewModel
import org.koin.dsl.module

/**
 * Koin模块
 */
val appModule = module {

    // Database
    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "moeking_database"
        ).build()
    }

    // DAOs
    single<CharacterDao> { get<AppDatabase>().characterDao() }
    single<TeamDao> { get<AppDatabase>().teamDao() }

    // Repositories
    single { CharacterRepository(get()) }
    single { TeamRepository(get()) }

    // ViewModels
    factory { CharacterViewModel(get()) }
    factory { TeamViewModel(get()) }
}
