package com.maks.courseproject

import android.app.Application
import androidx.fragment.app.Fragment
import com.maks.courseproject.di.AppComponent
import com.maks.courseproject.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()
}
fun Fragment.getAppComponent(): AppComponent =
    (requireContext().applicationContext as App).appComponent