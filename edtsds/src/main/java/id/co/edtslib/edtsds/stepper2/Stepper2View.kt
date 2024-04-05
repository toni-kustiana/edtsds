package id.co.edtslib.edtsds.stepper2

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.DsViewStepper2Binding

open class Stepper2View: FrameLayout {
    private var runnable: Runnable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val binding = DsViewStepper2Binding.inflate(LayoutInflater.from(context), this, true)
    var focused = false
    var showValueOnly = false
        set(_value) {
            field = _value

            value = value
        }

    var delegate: Stepper2Delegate? = null
    var delay = 500L
    var step = 1
    var valueAnimationDuration = 0L //100L
    var max = Int.MAX_VALUE
    var min = 0

    private var textWatcher: TextWatcher? = null

    var canEdit = false
        set(value) {
            field = value

            binding.tvValue.isVisible = ! value
            binding.etValue.isVisible = value
        }

    private var _value = 0
    var value
        set(lValue) {
            resetEditTextListener()

            drawValue(value = lValue, editing = false)

            setEditTextListener()

            if (binding.etValue.isFocused) {
                if (binding.etValue.text?.isNotEmpty() == true) {
                    binding.etValue.setSelection(binding.etValue.text!!.length)
                }
            }
        }
        get() = _value

    var maxLength = 0
        set(value) {
            field = value

            if (value > 0) {
                binding.etValue.filters = arrayOf(InputFilter.LengthFilter(value))
            }
        }

    init {
        setViewVisibility(0)
        binding.clExpand.isVisible = value > 0

        binding.btNew.setOnClickListener {
            if (value < max) {
                add(value+step)
            }
            else {
                delegate?.onReachMax(this)
            }
        }

        binding.btAdd.setOnClickListener {
            if (canEdit && focused) {
                hideKeyboard()
            }

            if (value < max) {
                add(value+step)
            }
            else {
                delegate?.onReachMax(this)
            }
        }

        binding.btMinus.setOnClickListener {
            if (canEdit && focused) {
                hideKeyboard()
            }

            if (value > min) {
                minus(value-step)
            }
            else {
                delegate?.onReachMin(this)
            }
        }

        binding.flSingleValue.setOnClickListener {
            expand()
        }

        binding.etValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etValue.clearFocus()
            }

            false
        }

        canEdit = false
        maxLength = 3
    }

    private fun changedValue(value: Int) {
        if (delay > 0) {
            if (runnable != null) {
                removeCallbacks(runnable)
                runnable = null
            }

            runnable = Runnable {
                delegate?.onValueChanged(this, value)
            }
            postDelayed(runnable, delay)
        }
        else {
            delegate?.onValueChanged(this, value)
        }
    }

    private fun setViewVisibility(_value: Int) {
        binding.btNew.isVisible = _value == 0
        binding.btAdd.isVisible = _value > 0
        binding.flSingleValue.isVisible = _value > 0 && showValueOnly
    }

    protected open fun add(p: Int) {
        showValueOnly = false
        value += step
        changedValue(value)

    }

    protected open fun minus(p: Int) {
        value -= step
        changedValue(value)
    }

    protected open fun expand() {
        showValueOnly = false
        value = value
        delegate?.onExpand(this, value)
    }

    private fun hideKeyboard() {
        binding.etValue.clearFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etValue.windowToken, 0)
    }

    private fun resetEditTextListener() {
        if (textWatcher != null) {
            binding.etValue.removeTextChangedListener(textWatcher)
        }

        binding.etValue.onFocusChangeListener = null
    }

    private fun setEditTextListener() {
        binding.etValue.setOnFocusChangeListener { _, hasFocus ->
            if (! hasFocus) {
                hideKeyboard()
                if (focused) {
                    if (binding.etValue.text?.isNotEmpty() == true) {
                        changedValue(value)
                    } else {
                        drawValue(value = 0, editing = false)
                        changedValue(0)
                    }
                }

                focused = false

            }
            else {
                focused = true
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val s = p0?.toString()
                if (s != null) {
                    try {
                        val d = s.toInt()
                        drawValue(value = d, editing = true)
                        changedValue(value)
                    }
                    catch (e: NumberFormatException) {
                        drawValue(value = value, editing = true)
                        changedValue(value)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.etValue.addTextChangedListener(textWatcher)
    }

    private fun drawValue(value: Int, editing: Boolean) {
        binding.btAdd.isActivated = value < max

        if (! showValueOnly) {
            binding.clExpand.isVisible = value > 0

            if (valueAnimationDuration > 0) {
                if (value != _value) {
                    val animation = if (value > _value)
                        TranslateAnimation(0.0f, 0.0f, -50.0f, 0.0f)
                    else
                        TranslateAnimation(0.0f, 0.0f, 0.0f, -50.0f)
                    animation.duration = valueAnimationDuration

                    binding.tvValue.startAnimation(animation)
                }
            }
            binding.tvValue.text = String.format("%d", value)
            if (! editing) {
                binding.etValue.setText(String.format("%d", value))
            }

            if (_value == 0 && value == step) {
                val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_in)

                anim.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        setViewVisibility(value)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                })
                binding.clExpand.startAnimation(anim)
            }
            else
                if (_value == step && value == 0)
                {
                    val anim = AnimationUtils.loadAnimation(context, R.anim.ds_slide_right_out)
                    anim.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            setViewVisibility(value)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })
                    binding.clExpand.startAnimation(anim)
                }
                else {
                    setViewVisibility(value)
                }

            _value = value
        }
        else {
            _value = value

            if (focused) {
                hideKeyboard()
            }
            binding.clExpand.isVisible = false
            binding.tvSingleValue.text =  String.format("%d", value)
            setViewVisibility(value)
        }
    }
}