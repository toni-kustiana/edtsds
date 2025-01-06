package id.co.edtslib.edtsds.stepper

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import id.co.edtslib.edtsds.R

open class StepperView: FrameLayout {
    private var textView: TextView? = null
    private var editText: EditText? = null

    private var min = 0
    private var max = Int.MAX_VALUE
    var step = 1
    var delegate: StepperDelegate? = null

    private var tvAdd: View? = null
    private var tvMinus: View? = null

    private var runnable:  Runnable? = null
    private var stepDisabled = false

    var delay = 500L
    var delayUi = 0L
    var delayEditing = 500L

    private var textWatcher: TextWatcher? = null
    private var textChangeRunnable: Runnable? = null

    var canEdit = false
        set(value) {
            field = value

            editText?.isVisible = value
            textView?.isVisible = ! value
        }

    var maxLength = 0
        set(value) {
            field = value

            if (value > 0) {
                editText?.filters = arrayOf(InputFilter.LengthFilter(value))
            }
        }

    var valueWidth = 0
        set(value) {
            field = value

            val dp40 = if (value == 0) resources.getDimensionPixelSize(R.dimen.dimen_40dp)
                else value
            val dp50 = if (value == 0) resources.getDimensionPixelSize(R.dimen.dimen_50dp)
                else value

            val layoutParams = textView?.layoutParams as LinearLayoutCompat.LayoutParams
            layoutParams.width = dp40

            val layoutParams1 = editText?.layoutParams as LinearLayoutCompat.LayoutParams
            layoutParams1.width = dp50

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
        editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText?.clearFocus()
            }

            false
        }
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (! hasFocus) {
                delegate?.onValueChanged(getValue())
            }
        }

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

        tvMinus = view.findViewById(R.id.tvMinus)
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
            setMinValue(min)

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
                val ivMinus = findViewById<AppCompatImageView?>(R.id.ivMinus)
                ImageViewCompat.setImageTintList(ivMinus, ContextCompat.getColorStateList(context, colorMinus))
            }

            val colorPlus = a.getResourceId(R.styleable.StepperView_textColorPlus, 0)
            if (colorPlus != 0) {
                val ivAdd = findViewById<AppCompatImageView?>(R.id.ivAdd)
                ivAdd?.imageTintList = ContextCompat.getColorStateList(context, colorPlus)
            }

            val colorValue = a.getResourceId(R.styleable.StepperView_textColorValue, 0)
            if (colorValue != 0) {
                textView?.setTextColor(ContextCompat.getColorStateList(context, colorValue))
            }

            canEdit = a.getBoolean(R.styleable.StepperView_canEdit, false)

            val lWidth = a.getDimension(R.styleable.StepperView_valueWidth, 0f)
            valueWidth = if (lWidth > 0f) {
                lWidth.toInt()
            } else {
                0
            }

            a.recycle()
        }
        else {
            canEdit = false
        }

        maxLength = 3
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

                            if (textChangeRunnable != null) {
                                editText?.removeCallbacks(textChangeRunnable)
                            }
                            textChangeRunnable = Runnable {
                                changedValue(d)
                            }
                            editText?.postDelayed(textChangeRunnable, delayEditing)
                        }
                    }
                    catch (_: NumberFormatException) {
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        editText?.addTextChangedListener(textWatcher)
    }

    open fun setValue(value: Int) {
        setText(String.format("%d", value))

        tvAdd?.isActivated = value+(step-1) < max
        tvMinus?.isActivated = value+(step-1) > min

        if (editText?.isFocused == true) {
            if (editText?.text?.isNotEmpty() == true) {
                editText?.setSelection(editText!!.text!!.length)
            }
        }
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
        editText?.hint = String.format("%d", value)

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

    protected open fun add() {
        try {
            val s = textView?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1 = d+step
                if (d1 > max) {
                    delegate?.onErrorMax()
                }

                if (d1 <= max) {
                    tvAdd?.isActivated = d1 < max
                    tvMinus?.isActivated = d1 > min
                    if (d != d1) {
                        setText(String.format("%d", d1))

                        tvAdd?.isActivated = d1 < max
                        changedValue(d1)
                    }
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    protected open fun minus() {
        try {
            val s = textView?.text?.toString()
            if (s != null) {
                val d = s.toInt()
                val d1  = d - step
                if (d1 < min) {
                    delegate?.onErrorMin()
                }

                if (d1 >= min) {
                    tvMinus?.isActivated = d1 > min
                    tvAdd?.isActivated = d1 < max
                    if (d != d1) {
                        setText(String.format("%d", d1))
                        changedValue(d1)
                        tvAdd?.isActivated = d1 < max
                    }
                }
            }
        } catch (ignored: NumberFormatException) {

        }
    }

    fun isEditing() = editText?.isFocused == true
    fun cancelEdit() {
        editText?.clearFocus()
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