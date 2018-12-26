package com.drgonfly.kotlinapp

import android.app.Application
import com.drgonfly.kotlinapp.view.state.StateLayout

class KotlinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        StateLayout.configStateId(R.layout.empty_view)
    }
}