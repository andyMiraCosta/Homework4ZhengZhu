package com.kangengineering.chapterseven

import android.app.Application
import com.kangengineering.chapterseven.di.appModules
import org.koin.core.context.startKoin

class ChapterSevenApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}