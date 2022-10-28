package com.example.updatinglanguagedynamically

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump

class UpdatingLanguageDynamicallyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        demoApplication = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Restring.init(this)
        ViewPump.init(RewordInterceptor)
    }

    companion object {
        var context: Context? = null
            private set
        var demoApplication: UpdatingLanguageDynamicallyApplication? = null
        operator fun get(activity: Activity): UpdatingLanguageDynamicallyApplication {
            return activity.application as UpdatingLanguageDynamicallyApplication
        }
    }
}