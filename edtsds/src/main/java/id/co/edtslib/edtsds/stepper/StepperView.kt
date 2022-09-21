package id.co.edtslib.edtsds.stepper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import id.co.edtslib.edtsds.R
import java.lang.NumberFormatException

class StepperView: FrameLayout {
    private var editText: EditText? = null
    private var min = 0
    private var max = Int.MAX_VALUE
    private var step = 1
    private var lastValue = -1
    var delegate: StepperDelegate? = null
    private var textWatcher: TextWatcher? = null

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

        editText = view.findViewById(R.id.editText)
        setEditTextListener()

        editText?.setOnFocusChangeListener { _, _ ->
            if (editText!!.text.toString().isEmpty()) {
                editText?.setText(String.format("%d", min))
            }
        }

        view.findViewById<View>(R.id.tvAdd).setOnClickListener {
            add()
        }
        view.findViewById<View>(R.id.tvMinus).setOnClickListener {
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

            editText?.setText(String.format("%d", value))

            val canInput = a.getBoolean(R.styleable.StepperView_canInput, false)
            editText?.isFocusable = canInput
            editText?.isClickable = canInput

            isActivated = canInput

            val dp40 = resources.getDimensionPixelSize(R.dimen.dimen_40dp)
            val dp56 = resources.getDimensionPixelSize(R.dimen.dimen_56dp)
            val dp8 = resources.getDimensionPixelSize(R.dimen.dimen_8dp)

            val layoutParams = editText?.layoutParams as LinearLayoutCompat.LayoutParams
            layoutParams.width = if (canInput) dp56 else dp40
            layoutParams.marginStart = if (canInput) dp8 else 0
            layoutParams.marginEnd = if (canInput) dp8 else 0

            a.recycle()
        }

    }

    fun getEditText() = editText

    fun removeEditTextListener() {
        if (textWatcher != null) {
            editText?.removeTextChangedListener(textWatcher)
        }
    }

    fun setEditTextListener() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.startsWith("0")) {
                        if (s.length > 1) {
                            editText?.setText(s.toString().trimStart('0'))
                        }
                        else
                            if (min > 0) {
                                removeEditTextListener()
                                editText?.setText(String.format("%d", min))
                                setEditTextListener()
                            }

                        delegate?.onChangeValue(min)
                    } else {
                        var value = getValue()
                        if (value > max && value > lastValue) {
                            delegate?.onErrorMax()
                            value = lastValue

                            removeEditTextListener()
                            editText?.setText(String.format("%d", value))
                            setEditTextListener()
                        } else {
                            lastValue = value
                        }

                        delegate?.onChangeValue(value)
                    }
                }
            }
        }

        editText?.addTextChangedListener(textWatcher)
    }

    fun setValue(value: Int) {
        editText?.setText(String.format("%d", value))
    }

    fun setMaxValue(value: Int) {
        max = value
    }

    fun setMaxValue(value: Int, force: Boolean) {
        if (force) {
            editText?.removeTextChangedListener(textWatcher)
        }
        max = value

        setEditTextListener()
    }

    fun setMinValue(value: Int) {
        min = value
    }

    private fun getValue(): Int {
        return try {
            val s = editText?.text?.toString()
            return s?.toInt() ?: min
        } catch (e: NumberFormatException) {
            min
        }
    }

    private fun add() {
        try {
            val s = editText?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1 = d+step
                if (d1 > max) {
                    delegate?.onErrorMax()
                }

                if (d1 <= max && d != d1) {
                    removeEditTextListener()
                    editText?.setText(String.format("%d", d1))
                    delegate?.onChangeValue(d1)
                    setEditTextListener()
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    private fun minus() {
        try {
            val s = editText?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1  = d - step
                if (d1 < min) {
                    delegate?.onErrorMin()
                }
                if (d1 >= min && d != d1) {
                    removeEditTextListener()
                    editText?.setText(String.format("%d", d1))
                    delegate?.onChangeValue(d1)
                    setEditTextListener()
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }
}