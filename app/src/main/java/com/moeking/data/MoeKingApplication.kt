package com.moeking.data

import android.app.Application
import com.moeking.data.api.NetworkModule
import com.moeking.data.di.appModule
import com.moeking.data.initializer.CharacterInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 萌王数据应用类
 */
class MoeKingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化Koin
        initKoin()

        // 初始化角色数据
        initializeCharacterData()
    }

    /**
     * 初始化Koin依赖注入
     */
    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MoeKingApplication)
            modules(appModule, NetworkModule)
        }
    }

    /**
     * 初始化角色数据
     */
    private fun initializeCharacterData() {
        // 使用后台线程初始化
        Thread {
            try {
                val database = (applicationContext as MoeKingApplication).getDatabase()
                val initializer = CharacterInitializer(applicationContext, database)
                initializer.initialize()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * 获取数据库实例
     */
    fun getDatabase() = (applicationContext as MoeKingApplication).database
}
