package id.co.edtslib.edtsds.stepper

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import id.co.edtslib.edtsds.R
import java.lang.NumberFormatException

class StepperView: FrameLayout {
    private var textView: TextView? = null
    private var min = 0
    private var max = Int.MAX_VALUE
    var step = 1
    var delegate: StepperDelegate? = null

    private var tvAdd: TextView? = null
    private var tvMinus: TextView? = null

    private var runnable:  Runnable? = null
    var delay = 500L

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

    private fun init(attrs: AttributeSet?) {
        val view = inflate(context, R.layout.view_stepper, this)

        textView = view.findViewById(R.id.textView)

        tvAdd = view.findViewById(R.id.tvAdd)
        tvAdd?.isActivated = true
        tvAdd?.setOnClickListener {
            add()
        }

        tvMinus = view.findViewById<TextView>(R.id.tvMinus)
        tvMinus?.setOnClickListener {
            minus()
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.StepperView,
                0, 0
            )

            min = a.getInt(R.styleable.StepperView_minValue,
                0)
            max = a.getInt(R.styleable.StepperView_maxValue,
                Int.MAX_VALUE)
            step = a.getInt(R.styleable.StepperView_step,
                1)

            val value = a.getInt(R.styleable.StepperView_value,
                1)

            textView?.text = String.format("%d", value)

            val bgMinus = a.getResourceId(R.styleable.StepperView_backgroundMinus, 0)
            if (bgMinus != 0) {
                tvMinus?.setBackgroundResource(bgMinus)
            }

            val bgPlus = a.getResourceId(R.styleable.StepperView_backgroundPlus, 0)
            if (bgPlus != 0) {
                tvAdd?.setBackgroundResource(bgPlus)
            }

            val bgValue = a.getResourceId(R.styleable.StepperView_backgroundValue, 0)
            if (bgValue != 0) {
                textView?.setBackgroundResource(bgValue)
            }

            val colorMinus = a.getResourceId(R.styleable.StepperView_textColorMinus, 0)
            if (colorMinus != 0) {
                tvMinus?.setTextColor(ContextCompat.getColorStateList(context, colorMinus))
            }

            val colorPlus = a.getResourceId(R.styleable.StepperView_textColorPlus, 0)
            if (colorPlus != 0) {
                tvAdd?.setTextColor(ContextCompat.getColorStateList(context, colorPlus))
            }

            val colorValue = a.getResourceId(R.styleable.StepperView_textColorValue, 0)
            if (colorValue != 0) {
                textView?.setTextColor(ContextCompat.getColorStateList(context, colorValue))
            }

            val dp40 = resources.getDimensionPixelSize(R.dimen.dimen_40dp)

            val layoutParams = textView?.layoutParams as LinearLayoutCompat.LayoutParams
            layoutParams.width = dp40

            a.recycle()
        }

    }

    fun setValue(value: Int) {
        textView?.text = String.format("%d", value)

        tvAdd?.isActivated = value < max
        tvMinus?.isActivated = value > min
    }

    private fun changedValue(value: Int) {
        if (delay > 0L) {
            if (runnable != null) {
                removeCallbacks(runnable)
                runnable = null
            }

            runnable = Runnable {
                delegate?.onChangeValue(value)
            }
            postDelayed(runnable, delay)
        }
        else {
            delegate?.onChangeValue(value)
        }
    }

    fun setMaxValue(value: Int) {
        max = value

        tvMinus?.isActivated = getValue() > min
        tvAdd?.isActivated = getValue() < max
    }

    fun setMinValue(value: Int) {
        min = value

        tvMinus?.isActivated = getValue() > min
        tvAdd?.isActivated = getValue() < max
    }

    private fun getValue(): Int {
        return try {
            val s = textView?.text?.toString()
            return s?.toInt() ?: min
        } catch (e: NumberFormatException) {
            min
        }
    }

    private fun add() {
        try {
            val s = textView?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1 = d+step
                if (d1 > max) {
                    delegate?.onErrorMax()
                }

                tvMinus?.isActivated = d1 > min
                tvAdd?.isActivated = d1 < max

                if (d1 <= max && d != d1) {
                    textView?.text = String.format("%d", d1)
                    tvAdd?.isActivated = d1 < max
                    changedValue(d1)
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    private fun minus() {
        try {
            val s = textView?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1  = d - step
                if (d1 < min) {
                    delegate?.onErrorMin()
                }

                tvMinus?.isActivated = d1 > min
                tvAdd?.isActivated = d1 < max

                if (d1 >= min && d != d1) {
                    textView?.text = String.format("%d", d1)
                    changedValue(d1)
                    tvAdd?.isActivated = d1 < max
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        tvMinus?.isEnabled = enabled
        tvAdd?.isEnabled = enabled
        textView?.isEnabled = enabled
    }
}