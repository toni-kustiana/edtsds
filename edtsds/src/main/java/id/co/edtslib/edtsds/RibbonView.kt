package id.co.edtslib.edtsds

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.databinding.ViewRibbonBinding

class RibbonView: FrameLayout {
    private var runnable: Runnable? = null
    var delay = 5000L

    constructor(context: Context) : super(context) {
        init(null)
    }
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

    enum class RibbonType {
        Success, Warning, Error
    }

    private val binding: ViewRibbonBinding =
        ViewRibbonBinding.inflate(LayoutInflater.from(context), this, true)

    var type = RibbonType.Success
        set(value) {
            field = value

            binding.root.isSelected = type == RibbonType.Error || type == RibbonType.Warning
            binding.root.isActivated = type == RibbonType.Error
        }

    private fun init(attrs: AttributeSet?) {
        if (! isInEditMode) {
            isVisible = false
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RibbonView,
                0, 0
            )

            val typeIdx = a.getInt(R.styleable.RibbonView_type, RibbonType.Success.ordinal)
            type = RibbonType.values()[typeIdx]

            a.recycle()
        }

        setOnClickListener {
            isVisible = false
        }
    }

    fun show(message: Int) {
        show(context.getString(message))
    }

    fun show(message: String?) {
        if (runnable != null) {
            removeCallbacks(runnable)
        }
        runnable = Runnable {
            isVisible = false
        }

        binding.textView.text = message

        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_top_in)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                postDelayed(runnable, delay)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        animation.duration = 500

        isVisible = true
        startAnimation(animation)
    }
}