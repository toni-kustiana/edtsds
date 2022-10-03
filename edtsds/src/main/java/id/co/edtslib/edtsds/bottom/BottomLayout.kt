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
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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

    private val binding: ViewBottomLayoutBinding =
        ViewBottomLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            binding.tvTitle.text = value
        }

    var tray = true
        set(value) {
            field = value
            binding.flTray.isVisible = tray
        }

    var snap = true
    var type = Type.Flat
        set(value) {
            field = value

            binding.vWindow.setBackgroundColor(ContextCompat.getColor(context, if (type == Type.Dialog) R.color.colorOpacity else android.R.color.transparent))
        }

    var layout = 0
        set(value) {
            field = value
            if (value != 0) {
                val layoutInflater = LayoutInflater.from(context)
                contentView = layoutInflater.inflate(value, null)
            }
            else {
                contentView = null
            }
        }


    private var lastY = 0f
    private var isDown = false
    private var downTime  = 0L
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

            val lType = a.getInt(R.styleable.BottomLayout_bottomLayoutType, 0)
            type = Type.values()[lType]

            layout = a.getResourceId(R.styleable.BottomLayout_layout, 0)

            a.recycle()
        }
        else {
            type = Type.Flat
        }

        setSwipeListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeListener() {
        binding.flBottom.setOnTouchListener { view, motionEvent ->
            if (tray) {
                val max = (binding.flBottom.height - binding.flTray.height).toFloat()
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastY = motionEvent.y
                        isDown = true
                        downTime = Date().time
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (! isDown) {
                            isDown = true
                            lastY = motionEvent.y
                            downTime = Date().time
                        }
                        else {
                            val y = (motionEvent.y - lastY)/2f
                            val newY = binding.flBottom.translationY + y
                            lastY = y

                            binding.flBottom.translationY = if (newY < 0) 0f else
                                if (newY > max) max
                                else newY
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
            return@setOnTouchListener true
        }
    }

    private fun onUp(motionEvent: MotionEvent) {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        isDown = false

        val y = (motionEvent.y - lastY)/2f
        val d = Date().time - downTime
        if (d < 1000) {
            val yAbs = abs(y)
            if (yAbs > 1) {
                binding.flBottom.translationY = if (y < 0) 0f else max
                checkDismiss()
            }
            else {
                snap()
            }
        }
        else {
            snap()
        }
    }

    private fun snap() {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        if (snap) {
            val mid = 2f*max/3f

            binding.flBottom.animate().
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    checkDismiss()
                }

                override fun onAnimationCancel(p0: Animator) {
                    checkDismiss()
                }

                override fun onAnimationRepeat(p0: Animator) {
                }

            }).
            translationY(if (binding.flBottom.translationY > mid) max else 0f)
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
}