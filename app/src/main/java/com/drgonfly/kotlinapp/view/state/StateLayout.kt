package com.drgonfly.kotlinapp.view.state

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.drgonfly.kotlinapp.R

open class StateLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    companion object {
        const val DEFAULT_LOADING_ID = -1
        const val DEFAULT_EMPTY_ID = -2
        const val DEFAULT_ERROR_ID = -3
        const val DEFAULT_OFFLINE_ID = -4

        var cfgLoadingId: Int = DEFAULT_LOADING_ID
        var cfgEmptyId: Int = DEFAULT_EMPTY_ID
        var cfgErrorId: Int = DEFAULT_ERROR_ID
        var cfgOfflineId: Int = DEFAULT_OFFLINE_ID

        /**
         * config the global state view id.
         * @param loadingId loading view id
         * @param emptyId empty view id
         * @param errorId error view id
         * @param offlineId offline view id
         */
        fun configStateId(loadingId: Int = DEFAULT_LOADING_ID, emptyId: Int = DEFAULT_EMPTY_ID,
                          errorId: Int = DEFAULT_ERROR_ID, offlineId: Int = DEFAULT_OFFLINE_ID) {
            cfgLoadingId = loadingId
            cfgEmptyId = emptyId
            cfgErrorId = errorId
            cfgOfflineId = offlineId
        }
    }

    private val defIdArray = arrayOf(DEFAULT_LOADING_ID, DEFAULT_EMPTY_ID, DEFAULT_ERROR_ID,
            DEFAULT_OFFLINE_ID)

    private var loadingView: View? = null
    private var emptyView: View? = null
    private var errorView: View? = null
    private var offlineView: View? = null

    private var listener: OnStateViewClickedListener? = null

    private lateinit var childMap: HashMap<Int, View>

    init {
        val array: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0)
        val loadingId = array.getResourceId(R.styleable.StateLayout_loading_layout, DEFAULT_LOADING_ID)
        val emptyId = array.getResourceId(R.styleable.StateLayout_empty_layout, DEFAULT_EMPTY_ID)
        val errorId = array.getResourceId(R.styleable.StateLayout_error_layout, DEFAULT_ERROR_ID)
        val offlineId = array.getResourceId(R.styleable.StateLayout_offline_layout, DEFAULT_OFFLINE_ID)
        array.recycle()

        loadingView = getView(loadingId)
        emptyView = getView(emptyId)
        errorView = getView(errorId)
        offlineView = getView(offlineId)

        emptyView?.setOnClickListener { listener?.onEmptyClicked() }
        errorView?.setOnClickListener { listener?.onErrorClicked() }
        offlineView?.setOnClickListener { listener?.onOfflineClicked() }
    }

    /**
     * Set the state view children's click listener.
     * @param listener the listener
     * @param childIds children's id
     */
    fun setOnStateChildClickedListener(listener: OnStateViewChildClickedListener?, vararg childIds: Int) {
        childMap = HashMap()

        for (childId in childIds) {
            val view: View? = when {
                emptyView?.findViewById<View>(childId) != null -> emptyView?.findViewById(childId)
                errorView?.findViewById<View>(childId) != null -> errorView?.findViewById(childId)
                else -> offlineView?.findViewById(childId)
            }
            if (view != null) {
                childMap[childId] = view
            }
        }

        for (key in childMap.keys) {
            childMap[key]?.setOnClickListener { listener?.onChildClicked(key, childMap[key]) }
        }
    }

    /**
     * Set the state view's click listener.
     * @param listener the listener of state view
     */
    fun setOnStateViewClickedListener(listener: OnStateViewClickedListener?) {
        this.listener = listener
    }

    /**
     * Show loading state view.
     * @param witAnimation if show the view with animation
     */
    fun showLoading(witAnimation: Boolean = false) {
        showStateView(loadingView, witAnimation)
    }

    /**
     * Show empty state view.
     * @param witAnimation if show the view with animation
     */
    fun showEmpty(witAnimation: Boolean = false) {
        showStateView(emptyView, witAnimation)
    }

    /**
     * Show error state view.
     * @param witAnimation if show the view with animation
     */
    fun showError(witAnimation: Boolean = false) {
        showStateView(errorView, witAnimation)
    }

    /**
     * Show offline state view.
     * @param witAnimation if show the view with animation
     */
    fun showOffline(witAnimation: Boolean = false) {
        showStateView(offlineView, witAnimation)
    }

    /**
     * Show success state view.
     * @param witAnimation if show the view with animation
     */
    fun showContent(witAnimation: Boolean = false) {
        removeStateView()
        showChildren(witAnimation)
    }

    private fun showStateView(view: View?, withAnimation: Boolean) {
        removeStateView()
        hideChildren()
        addView(view)
        if (withAnimation) {
            withAnimation(view)
        }
    }

    private fun withAnimation(view: View?) {
        ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f).start()
        ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f).start()
    }

    private fun removeStateView() {
        removeView(loadingView)
        removeView(emptyView)
        removeView(errorView)
        removeView(offlineView)
    }

    private fun showChildren(witAnimation: Boolean = false) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.visibility = View.VISIBLE
            if (witAnimation) {
                withAnimation(child)
            }
        }
    }

    private fun hideChildren() {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.visibility = GONE
        }
    }

    private fun getView(viewId: Int): View {
        val view: View
        if (viewId !in defIdArray) {
            view = LayoutInflater.from(context).inflate(viewId, null)
        } else {
            view = when (viewId) {
                DEFAULT_LOADING_ID -> {
                    if (cfgLoadingId == DEFAULT_LOADING_ID) {
                        getTextView("loading")
                    } else {
                        LayoutInflater.from(context).inflate(cfgLoadingId, null)
                    }
                }
                DEFAULT_EMPTY_ID -> {
                    if (cfgEmptyId == DEFAULT_EMPTY_ID) {
                        getTextView("no data")
                    } else {
                        LayoutInflater.from(context).inflate(cfgEmptyId, null)
                    }
                }
                DEFAULT_ERROR_ID -> {
                    if (cfgErrorId == DEFAULT_ERROR_ID) {
                        getTextView("error")
                    } else {
                        LayoutInflater.from(context).inflate(cfgErrorId, null)
                    }
                }
                DEFAULT_OFFLINE_ID -> {
                    if (cfgOfflineId == DEFAULT_OFFLINE_ID) {
                        getTextView("offline")
                    } else {
                        LayoutInflater.from(context).inflate(cfgOfflineId, null)
                    }
                }
                else -> {
                    //This should never happen
                    getTextView("unknown state")
                }
            }
        }
        return view
    }

    private fun getTextView(text: String): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.width = width
        textView.height = height
        textView.gravity = Gravity.CENTER
        return textView
    }

    interface OnStateViewClickedListener {
        /**
         * when the empty state view being clicked,this method will be call.
         */
        fun onEmptyClicked()

        /**
         * when the error state view being clicked,this method will be call.
         */
        fun onErrorClicked()

        /**
         * when the offline state view being clicked,this method will be call.
         */
        fun onOfflineClicked()
    }

    interface OnStateViewChildClickedListener {

        /**
         * when the state view's subview being clicked,this method will be call.
         * @param childId id of the subview being clicked
         * @param view subview being clicked
         */
        fun onChildClicked(childId: Int, view: View?)
    }

}