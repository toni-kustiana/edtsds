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

open class Stepper2View: FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val binding = DsViewStepper2Binding.inflate(LayoutInflater.from(context), this, true)
    var showValueOnly = false
        set(_value) {
            field = _value

            value = value
        }

    var delegate: Stepper2Delegate? = null
    var valueAnimationDuration = 0L //100L
    var max = Int.MAX_VALUE
    var min = 0
    var value = 0
        set(_value) {
            if (! showValueOnly) {
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
                            setViewVisibility(_value)
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
                            setViewVisibility(_value)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })
                    binding.clExpand.startAnimation(anim)
                }
                else {
                    setViewVisibility(_value)
                }

                field = _value
                delegate?.onValueChanged(_value)
            }
            else {
                field = _value
                delegate?.onValueChanged(_value)

                binding.clExpand.isVisible = false
                binding.tvSingleValue.text =  String.format("%d", _value)
                setViewVisibility(_value)
            }

        }

    init {
        setViewVisibility(0)
        binding.clExpand.isVisible = value > 0

        binding.btNew.setOnClickListener {
            if (value < max) {
                add(value+1)
            }
        }

        binding.btAdd.setOnClickListener {
            if (value < max) {
                add(value+1)
            }
        }

        binding.btMinus.setOnClickListener {
            if (value > min) {
                minus(value-1)
            }
        }

        binding.flSingleValue.setOnClickListener {
            showValueOnly = false
            value = value
        }
    }

    private fun setViewVisibility(_value: Int) {
        binding.btNew.isVisible = _value == 0
        binding.btAdd.isVisible = _value > 0
        binding.flSingleValue.isVisible = _value > 0 && showValueOnly
    }

    protected open fun add(p: Int) {
        showValueOnly = false
        value++
    }

    protected open fun minus(p: Int) {
        value--
    }
}