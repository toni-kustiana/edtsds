package id.co.edtslib.edtsds.bottom

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewBottomLayoutBinding
import java.util.Date
import kotlin.math.abs

class BottomLayout: FrameLayout {
    enum class Type {
        Flat, Dialog
    }
    var delegate: BottomLayoutDelegate? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    val binding: ViewBottomLayoutBinding =
        ViewBottomLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    var canceledOnTouchOutside = false
        set(value) {
            field = value
            if (value) {
                binding.vWindow.setOnClickListener {
                    delegate?.onDismiss()
                }
            }
            else {
                binding.vWindow.setOnClickListener(null)
            }
        }

    var scrollView: View? = null
        set(value) {
            field = value
            setScrollViewListener(value)
        }

    var bottomHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        set(value) {
            field = value
            if (value != ViewGroup.LayoutParams.WRAP_CONTENT) {
                val layoutParams = binding.flBottom.layoutParams as LayoutParams
                layoutParams.height = value

                val layoutParams1 = binding.llBottom.layoutParams as LayoutParams
                layoutParams1.height = ViewGroup.LayoutParams.MATCH_PARENT

                val layoutParams2 = binding.flContent.layoutParams as LinearLayout.LayoutParams
                layoutParams2.weight = 1f
                layoutParams2.height = 0
            }
        }

    var isOverlay: Boolean? = null
        set(value) {
            field = value

            val color = if (value != false) R.color.colorOpacity else android.R.color.transparent
            binding.vWindow.setBackgroundColor(ContextCompat.getColor(context, color))
        }

    var title: String? = null
        set(value) {
            field = value
            binding.tvTitle.text = value

            binding.flTitle.isVisible = value?.isNotEmpty() == true || cancelable || ! popup
        }

    var tray = true
        set(value) {
            field = value
            binding.flTray.isVisible = tray
        }

    var snap = true
    var halfSnap = false
    var type = Type.Flat
        set(value) {
            field = value

            binding.vWindow.setBackgroundColor(ContextCompat.getColor(context, if (type == Type.Dialog) R.color.colorOpacity else android.R.color.transparent))
            binding.flRoot.isFocusable = value == Type.Dialog
            binding.flRoot.isClickable = value == Type.Dialog
        }

    var popup: Boolean = false
        set(value) {
            field = value
            binding.ivBack.isVisible = value
            binding.ivBack.setOnClickListener {
                isVisible = false
            }
            if (value) {
                cancelable = false
            }

            binding.flTitle.isVisible = title?.isNotEmpty() == true || cancelable || ! value
        }

    var cancelable = false
        set(value) {
            field = value
            binding.ivCancel.isVisible = cancelable
            binding.ivCancel.setOnClickListener {
                isVisible = false
            }

            binding.flTitle.isVisible = title?.isNotEmpty() == true || cancelable || ! popup
        }

    var contentLayout = 0
        set(value) {
            field = value
            contentView = if (value != 0) {
                val layoutInflater = LayoutInflater.from(context)
                layoutInflater.inflate(value, null)
            } else {
                null
            }
        }

    var titleLayout = 0
        set(value) {
            field = value
            titleView = if (value != 0) {
                val layoutInflater = LayoutInflater.from(context)
                layoutInflater.inflate(value, null)
            } else {
                null
            }
        }

    var titleDivider = true
        set(value) {
            field = value
            binding.vLine.isVisible = value
        }

    var marginTop = 0f
        set(value) {
            field = value
            val layoutParams = binding.flBottom.layoutParams as LayoutParams
            layoutParams.topMargin = value.toInt()
        }

    private var originalRawY = 0f
    private var rawY = 0f
    private var isDown = false
    private var downTime  = 0L
    private var fullHeight = false
    var contentView: View? = null
        set(value) {
            if (field != null) {
                binding.flContent.removeView(field)
            }

            field = value
            if (value != null) {
                binding.flContent.addView(value)
            }
        }
    var titleView: View? = null
        set(value) {
            field = value
            if (value != null) {
                binding.flTitle.removeAllViews()
                binding.flTitle.addView(value)
            }
        }



    private fun init(attrs: AttributeSet?) {
        if (layoutParams is ViewGroup.LayoutParams) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BottomLayout,
                0, 0
            )

            tray = a.getBoolean(R.styleable.BottomLayout_tray, true)
            snap = a.getBoolean(R.styleable.BottomLayout_snap, true)
            title = a.getString(R.styleable.BottomLayout_title)
            cancelable = a.getBoolean(R.styleable.BottomLayout_cancelable, false)
            popup = a.getBoolean(R.styleable.BottomLayout_popup, false)
            halfSnap = a.getBoolean(R.styleable.BottomLayout_popup, false)
            marginTop = a.getDimension(R.styleable.BottomLayout_marginTop,
                context.resources.getDimension(R.dimen.dimen_bottom_layout_margin_top))


            val lType = a.getInt(R.styleable.BottomLayout_bottomLayoutType, 0)
            type = Type.values()[lType]

            val lTitleLayout = a.getResourceId(R.styleable.BottomLayout_titleLayout, 0)
            if (lTitleLayout != 0) {
                titleLayout = lTitleLayout
            }

            contentLayout = a.getResourceId(R.styleable.BottomLayout_contentLayout, 0)

            a.recycle()
        }
        else {
            type = Type.Flat
            cancelable = false
            popup = false
        }

        setSwipeListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeListener() {
        binding.flBottom.setOnTouchListener { view, motionEvent ->
            processEvent(motionEvent)
            view.performClick()
            return@setOnTouchListener true
        }
    }

    private fun processEvent(motionEvent: MotionEvent) {
        if (tray) {
            val max = (binding.flBottom.height - binding.flTray.height).toFloat()
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    originalRawY = motionEvent.rawY
                    rawY = motionEvent.rawY - binding.flBottom.translationY
                    isDown = true
                    downTime = Date().time
                }
                MotionEvent.ACTION_MOVE -> {
                    if (! isDown) {
                        isDown = true
                        originalRawY = motionEvent.rawY
                        rawY = motionEvent.rawY - binding.flBottom.translationY
                        downTime = Date().time
                    }
                    else {
                        val dy = motionEvent.rawY - rawY
                        val newY =  if (dy < 0f) 0f else if (dy > max) max else dy
                        binding.flBottom.animate().setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator) {
                            }

                            @SuppressLint("ClickableViewAccessibility")
                            override fun onAnimationEnd(p0: Animator) {
                                if (newY == 0f && fullHeight && binding.flBottom.height >= binding.flRoot.height) {

                                    binding.flBottom.isSelected = true
                                    binding.flTray.isVisible = false
                                    binding.flBottom.setOnTouchListener { view, motionEvent ->
                                        return@setOnTouchListener false
                                    }
                                }
                            }

                            override fun onAnimationCancel(p0: Animator) {
                            }

                            override fun onAnimationRepeat(p0: Animator) {
                            }

                        }).translationY(newY).setDuration(0L).start()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    onUp(motionEvent)
                }
                MotionEvent.ACTION_CANCEL -> {
                    onUp(motionEvent)
                }
            }
        }
    }

    fun showHalf() {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        binding.flBottom.translationY = max/2f
    }

    private fun onUp(motionEvent: MotionEvent) {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        isDown = false

        val dy = motionEvent.rawY - rawY
        val newY =  if (dy < 0f) 0f else if (dy > max) max else dy

        val d = Date().time - downTime
        if (d < 100) {
            val dy1 = motionEvent.rawY - originalRawY
            if (abs(dy1) > 10) {
                if (dy1 > 0) {
                    binding.flBottom.animate().translationY(max).setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {
                        }

                        override fun onAnimationEnd(p0: Animator) {
                            delegate?.onCollapse()
                            checkDismiss()
                        }

                        override fun onAnimationCancel(p0: Animator) {
                            checkDismiss()
                        }

                        override fun onAnimationRepeat(p0: Animator) {
                            checkDismiss()
                        }

                    })
                }
                else {
                    binding.flBottom.animate().translationY(0f).setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {
                        }

                        override fun onAnimationEnd(p0: Animator) {
                            delegate?.onExpand()

                        }

                        override fun onAnimationCancel(p0: Animator) {
                        }

                        override fun onAnimationRepeat(p0: Animator) {
                        }

                    })
                }
            }
            return
        }

        binding.flBottom.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                snap()
            }

            override fun onAnimationCancel(p0: Animator) {
                snap()
            }

            override fun onAnimationRepeat(p0: Animator) {
                snap()
            }

        }).translationY(newY).setDuration(0L).start()
    }

    private fun snap() {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        if (snap) {
            val mid = 2f*max/3f
            val snapY = if (halfSnap) {
                    if (binding.flBottom.translationY < 1f*max/3f) {
                        0f
                    }
                    else
                        if (binding.flBottom.translationY < 2f*max/3f) {
                            1f*max/2f
                        }
                        else {
                            max
                        }

                }
                else if (binding.flBottom.translationY > mid) max else 0f

            binding.flBottom.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    if (snapY == max) {
                        delegate?.onCollapse()
                    }
                    else if (snapY == 0f) {
                        delegate?.onExpand()
                    }
                    binding.flBottom.translationY = snapY
                    checkDismiss()
                }

                override fun onAnimationCancel(p0: Animator) {
                    checkDismiss()
                }

                override fun onAnimationRepeat(p0: Animator) {
                }

            }).translationY(snapY).setDuration(0L).start()
        }
        else {
            checkDismiss()
        }
    }

    private fun checkDismiss() {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()

        if (type == Type.Dialog) {
            if (binding.flBottom.translationY == max) {
                isVisible = false
            }
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility != GONE) {
            binding.flBottom.translationY = 0f
        }
        else {
            if (type == Type.Dialog) {
                delegate?.onDismiss()
            }
        }
    }

    fun showAnimate() {
        binding.flBottom.isInvisible = true
        post {
            binding.flBottom.translationY = binding.flBottom.height.toFloat()
            binding.flBottom.isVisible = true
            binding.flBottom.animate().translationY(0f)

        }
    }

    private fun setScrollViewListener(view: View?) {
        if (view is RecyclerView) {
            view.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    if (rv.layoutManager is LinearLayoutManager) {
                        val linearLayoutManager = rv.layoutManager as LinearLayoutManager
                        val index = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                        if (index == 0 || binding.flBottom.translationY > 0) {
                            processEvent(e)
                        }
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }
            })
        }

    }
}