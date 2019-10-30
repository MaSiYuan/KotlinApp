package com.drgonfly.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.drgonfly.kotlinapp.util.Utils
import com.drgonfly.kotlinapp.view.state.StateLayout
import com.drgonfly.kotlinapp.view.text.ImgTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StateLayout.OnStateViewChildClickedListener, StateLayout.OnStateViewClickedListener {

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

        img_text.setOnClickListener { img_text.showText("老哥，遇到这样的公司我的建议是果断离职吧，或者找项目经理来协调这件事情，比如我们公司的迭代周期是两周，在需求讨论会之后大家需要把各端的排期定好，比如设计排期，接口排期，然后移动端制定移动端排期，接口不合理就得根据移动端业务场景修改，个人协调不好找移动端负责人和后端负责人协调沟通。即使有了这些保证我们上线也是会遇到因为接口问题上线推迟到凌晨，整的我都有点怀疑人生了，所以我才想着提升自己进大公司，把跟他们扯皮的时间用来学习上，所以总结下来找一个好的团队才是解决这个问题的关键。") }

        img_text.addImgEngine(object : ImgTextView.ImgEngine {
            override fun showImg(iv: ImageView) {
                iv.setImageResource(R.drawable.ic_launcher_foreground)
            }
        })

        circle_img.setImageBitmap(Utils.getAvatar(800, R.drawable.head, this))
//        Glide.with(this).load(Utils.getAvatar(600, R.drawable.head, this)).into(circle_img)
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
