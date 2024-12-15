package com.Homework4ZhengZhu.hw4

import android.app.Application
import com.Homework4ZhengZhu.hw4.di.appModules
import org.koin.core.context.startKoin

class ChapterSevenApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}