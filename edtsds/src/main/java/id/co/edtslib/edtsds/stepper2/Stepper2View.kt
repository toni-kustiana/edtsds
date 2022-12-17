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

    var delegate: Stepper2Delegate? = null
    var valueAnimationDuration = 100L
    var max = Int.MAX_VALUE
    var min = 0
    var value = 0
        set(value) {
            if (valueAnimationDuration > 0) {
                val animation = if (value > field)
                    TranslateAnimation(0.0f, 0.0f, -50.0f, 0.0f)
                else
                    TranslateAnimation(0.0f, 0.0f, 0.0f, -50.0f)
                animation.duration = valueAnimationDuration

                binding.tvValue.startAnimation(animation)
            }
            binding.tvValue.text = String.format("%d", value)

            binding.clExpand.isVisible = value > 0

            if (field == 0 && value == 1) {
                val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_in)

                anim.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        binding.btNew.isVisible = value == 0
                        binding.btAdd.isVisible = value > 0
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
                binding.clExpand.startAnimation(anim)
            }
            else
            if (field == 1 && value == 0)
            {
                val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_out)
                anim.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        binding.btNew.isVisible = value == 0
                        binding.btAdd.isVisible = value > 0
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
                binding.clExpand.startAnimation(anim)
            }
            else {
                binding.btNew.isVisible = value == 0
                binding.btAdd.isVisible = value > 0
            }

            field = value
            delegate?.onChangeValue(value)

            setLeftWidth()

        }

    init {
        binding.btNew.isVisible = value == 0
        binding.btAdd.isVisible = value > 0
        binding.clExpand.isVisible = value > 0

        binding.btNew.setOnClickListener {
            add()
        }

        binding.btAdd.setOnClickListener {
            add()
        }

        binding.btMinus.setOnClickListener {
            minus()
        }

        post {
            setLeftWidth()
        }
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