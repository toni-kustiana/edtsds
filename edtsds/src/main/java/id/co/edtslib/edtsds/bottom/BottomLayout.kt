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
import androidx.core.view.isInvisible
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

    var cancelable = false
        set(value) {
            field = value
            binding.ivCancel.isVisible = cancelable
            binding.ivCancel.setOnClickListener {
                isVisible = false
            }
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


    private var rawY = 0f
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
                        rawY = motionEvent.rawY - binding.flBottom.translationY
                        isDown = true
                        downTime = Date().time
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (! isDown) {
                            isDown = true
                            rawY = motionEvent.rawY - binding.flBottom.translationY
                            downTime = Date().time
                        }
                        else {
                            val dy = motionEvent.rawY - rawY
                            val newY =  if (dy < 0f) 0f else if (dy > max) max else dy
                            binding.flBottom.animate().setListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(p0: Animator) {
                                }

                                override fun onAnimationEnd(p0: Animator) {
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
            view.performClick()
            return@setOnTouchListener true
        }
    }

    private fun onUp(motionEvent: MotionEvent) {
        val max = (binding.flBottom.height - binding.flTray.height).toFloat()
        isDown = false

        val dy = motionEvent.rawY - rawY
        var newY =  if (dy < 0f) 0f else if (dy > max) max else dy

        val d = Date().time - downTime
        if (d < 1000) {
            val yAbs = abs(y)
            if (yAbs > 2) {
                newY = if (dy < 0) 0f else max
            }
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
            val snapY = if (binding.flBottom.translationY > mid) max else 0f

            binding.flBottom.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    binding.flBottom.translationY = snapY
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }

            }).translationY(snapY).setDuration(0L).start()

            /*binding.flBottom.animate().
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
            translationY(snapY).start()*/
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
}