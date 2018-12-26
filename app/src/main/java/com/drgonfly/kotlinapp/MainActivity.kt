package com.drgonfly.kotlinapp

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load.setOnClickListener { state_layout.showLoading(true) }
        empty.setOnClickListener { state_layout.showEmpty(true) }
        error.setOnClickListener { state_layout.showError(true) }
        offline.setOnClickListener { state_layout.showOffline(true) }
        success.setOnClickListener { state_layout.showContent(true) }

        decor.setOnClickListener {
        }
    }
}
