package com.maks.courseproject

import android.app.Application
import androidx.fragment.app.Fragment
import com.maks.courseproject.di.AppComponent
import com.maks.courseproject.di.AppModule
import com.maks.courseproject.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(context = this)).build()
    }
}

fun Fragment.getAppComponent(): AppComponent =
    (requireContext().applicationContext as App).appComponent