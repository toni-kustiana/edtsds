package id.co.edtslib.edtsds.stepper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R

class StepperView: FrameLayout {
    private var textView: TextView? = null
    private var editText: EditText? = null

    private var min = 0
    private var max = Int.MAX_VALUE
    var step = 1
    var delegate: StepperDelegate? = null

    private var tvAdd: TextView? = null
    private var tvMinus: TextView? = null

    private var runnable:  Runnable? = null
    private var stepDisabled = false

    var delay = 500L
    var delayUi = 0L
    private var textWatcher: TextWatcher? = null

    var canEdit = false
        set(value) {
            field = value

            editText?.isVisible = value
            textView?.isVisible = ! value
        }

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
        editText = view.findViewById(R.id.editText)

        tvAdd = view.findViewById(R.id.tvAdd)
        tvAdd?.isActivated = true
        tvAdd?.setOnClickListener {
            if (canEdit) {
                hideKeyboard()
            }

            if (stepDisabled.not()) {
                if (delayUi != 0L) {
                    stepDisabled = true
                }
                add()

                if (delayUi != 0L) {
                    postDelayed({
                        stepDisabled = false
                    }, delayUi)
                }
            }
        }

        tvMinus = view.findViewById<TextView>(R.id.tvMinus)
        tvMinus?.setOnClickListener {
            if (canEdit) {
                hideKeyboard()
            }

            if (stepDisabled.not()) {
                if (delayUi != 0L) {
                    stepDisabled = true
                }
                minus()
                if (delayUi != 0L) {
                    postDelayed({
                        stepDisabled = false
                    }, delayUi)
                }
            }
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

            setText(String.format("%d", value))

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

            canEdit = a.getBoolean(R.styleable.StepperView_canEdit, false)

            a.recycle()
        }
        else {
            canEdit = false
        }
    }

    private fun setText(text: String?, editing: Boolean = false) {
        if (textWatcher != null) {
            editText?.removeTextChangedListener(textWatcher)
        }
        textView?.text = text
        if (! editing) {
            editText?.setText(text)
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val s = p0?.toString()
                if (s != null) {
                    try {
                        val d = s.toInt()
                        if (d > max) {
                            delegate?.onErrorMax()
                        }

                        tvMinus?.isActivated = d > min
                        tvAdd?.isActivated = d < max

                        if (d <= max) {
                            setText(String.format("%d", d), true)

                            tvAdd?.isActivated = d < max
                            changedValue(d)
                        }
                    }
                    catch (e: NumberFormatException) {
                        setText(String.format("%d", min), true)
                        changedValue(min)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        editText?.addTextChangedListener(textWatcher)
    }

    fun setValue(value: Int) {
        setText(String.format("%d", value))

        tvAdd?.isActivated = value < max
        tvMinus?.isActivated = value > min
    }

    private fun changedValue(value: Int) {
        delegate?.onValueChanged(value)
        if (delay > 0L) {
            if (runnable != null) {
                removeCallbacks(runnable)
                runnable = null
            }

            runnable = Runnable {
                delegate?.onSubmit(value)
            }
            postDelayed(runnable, delay)
        }
        else {
            delegate?.onSubmit(value)
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

    fun getValue(): Int {
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
                    setText(String.format("%d", d1))

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
                    setText(String.format("%d", d1))
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

    private fun hideKeyboard() {
        editText?.clearFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText?.windowToken, 0)
    }
}