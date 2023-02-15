package com.example.sistemainfo

import android.app.Application
import com.example.sistemainfo.inject.AppComponent
import com.example.sistemainfo.inject.DaggerAppComponent


open class SistemaInfoApp : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}



