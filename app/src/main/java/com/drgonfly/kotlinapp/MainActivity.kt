package com.drgonfly.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.drgonfly.kotlinapp.view.state.StateLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StateLayout.OnStateViewChildClickedListener,StateLayout.OnStateViewClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load.setOnClickListener { state_layout.showLoading(true) }
        empty.setOnClickListener { state_layout.showEmpty(true) }
        error.setOnClickListener { state_layout.showError(true) }
        offline.setOnClickListener { state_layout.showOffline(true) }
        success.setOnClickListener { state_layout.showContent(true) }

        state_layout.setOnStateChildClickedListener(this,
                R.id.empty_button,
                R.id.error_button,
                R.id.offline_button)

        state_layout.setOnStateViewClickedListener(this)

    }

    override fun onChildClicked(childId: Int, view: View?) {
        when (childId) {
            R.id.empty_button -> {
                Toast.makeText(this, "empty child clicked.", Toast.LENGTH_LONG).show()
            }
            R.id.error_button -> {
                Toast.makeText(this, "error child clicked.", Toast.LENGTH_LONG).show()
            }
            R.id.offline_button -> {
                Toast.makeText(this, "offline child clicked.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onErrorClicked() {
        Toast.makeText(this, "error clicked.", Toast.LENGTH_LONG).show()
    }

    override fun onOfflineClicked() {
        Toast.makeText(this, "offline clicked.", Toast.LENGTH_LONG).show()
    }

    override fun onEmptyClicked() {
        Toast.makeText(this, "empty clicked.", Toast.LENGTH_LONG).show()
    }
}
