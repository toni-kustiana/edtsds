package id.co.edtslib.edtsds.stepper2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.DsViewStepper2Binding

class Stepper2View: FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val binding = DsViewStepper2Binding.inflate(LayoutInflater.from(context), this, true)
    private var clicked = false

    var delegate: Stepper2Delegate? = null
    var valueAnimationDuration = 100L
    var max = Int.MAX_VALUE
    var min = 0
    var value = 0
        set(_value) {
            if (clicked) {
                binding.clExpand.isVisible = _value > 0

                if (valueAnimationDuration > 0) {
                    if (_value != field) {
                        val animation = if (_value > field)
                            TranslateAnimation(0.0f, 0.0f, -50.0f, 0.0f)
                        else
                            TranslateAnimation(0.0f, 0.0f, 0.0f, -50.0f)
                        animation.duration = valueAnimationDuration

                        binding.tvValue.startAnimation(animation)
                    }
                }
                binding.tvValue.text = String.format("%d", _value)

                if (field == 0 && _value == 1) {
                    val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_in)

                    anim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            setViewVisibility()
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })
                    binding.clExpand.startAnimation(anim)
                }
                else
                if (field == 1 && _value == 0)
                {
                    val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_out)
                    anim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            setViewVisibility()
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })
                    binding.clExpand.startAnimation(anim)
                }
                else {
                    setViewVisibility()
                }

                field = _value
                delegate?.onChangeValue(_value)

                setLeftWidth()
            }
            else {
                field = _value
                delegate?.onChangeValue(_value)

                binding.clExpand.isVisible = false
                binding.tvSingleValue.text =  String.format("%d", _value)
                setViewVisibility()
            }

        }

    init {
        setViewVisibility()
        binding.clExpand.isVisible = value > 0

        binding.btNew.setOnClickListener {
            if (isEnabled) {
                clicked = true
                add()
            }
        }

        binding.btAdd.setOnClickListener {
            if (isEnabled) {
                add()
            }
        }

        binding.btMinus.setOnClickListener {
            if (isEnabled) {
                minus()
            }
        }

        binding.flSingleValue.setOnClickListener {
            if (isEnabled) {
                clicked = true
                value = value
            }
        }

        post {
            setLeftWidth()
        }
    }

    private fun setViewVisibility() {
        binding.btNew.isVisible = value == 0
        binding.btAdd.isVisible = value > 0
        binding.flSingleValue.isVisible = value > 0 && ! clicked
    }

    private fun setLeftWidth() {
        val w = binding.clRoot.width

        val dp16 = context.resources.getDimensionPixelSize(R.dimen.dimen_16dp)
        val newW = w - dp16
        val layoutParams = binding.flLeft.layoutParams
        if (newW != layoutParams.width) {
            layoutParams.width = newW
        }
    }

    private fun add() {
        if (value < max) {
            value++
        }
    }

    private fun minus() {
        if (value > min) {
            value--
        }
    }
}