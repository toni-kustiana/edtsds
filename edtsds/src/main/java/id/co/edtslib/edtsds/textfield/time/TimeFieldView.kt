package id.co.edtslib.edtsds.textfield.time

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.databinding.DsTimeFieldSpinnerBinding
import id.co.edtslib.edtsds.databinding.DsViewTimeFieldBinding
import id.co.edtslib.edtsds.databinding.DsViewTimePickerBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TimeFieldView : FrameLayout {

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context) : super(context) {
        init(null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    enum class ClockType {
        Clock, Spinner
    }

    private val binding = DsViewTimeFieldBinding.inflate(LayoutInflater.from(context), this, true)
    private var selectedTime: LocalTime? = null

    var fieldEnabled: Boolean = true
        set(value) {
            field = value
            binding.root.isEnabled = value
            binding.clContent.isEnabled = value
        }

    var delegate: TimeFieldDelegate? = null

    var spinnerTitle: String? = null
    var spinnerButtonText: String? = null
    var showIcon = true
        set(value) {
            field = value
            binding.imageView.isVisible = value
        }

    var clockType = ClockType.Clock

    var time: LocalTime? = null
        set(value) {
            field = value
            setTextValue()

            if (value != null) {
                val dtf = DateTimeFormatter.ofPattern(format)
                delegate?.onTimeChanged(value, dtf.format(value))
            }

            binding.tvLabel.isVisible = autoShowLabel != false || value != null
        }

    var format = "HH:mm"

    var hint: String? = null
        set(value) {
            field = value
            setTextValue()
        }

    var label: String? = null
        set(value) {
            field = value
            binding.tvLabel.text = label
        }

    var autoShowLabel: Boolean? = null
        set(value) {
            field = value
            binding.tvLabel.isVisible = value != false
        }

    var error: String? = null
        set(value) {
            field = value

            binding.tvError.isVisible = value?.isNotEmpty() == true
            binding.tvError.text = value

            isSelected = value?.isNotEmpty() == true
        }


    private fun setTextValue() {
        binding.tvValue.text = if (time == null) hint else {
            val dtf = DateTimeFormatter.ofPattern(format)
            time!!.format(dtf)
        }
        binding.tvValue.isActivated = time != null
    }

    private fun init(attrs: AttributeSet?) {
        error = null

        binding.editText.setOnFocusChangeListener { v, b ->
            if (b) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
            isActivated = b
            binding.tvValue.isActivated = time != null

            binding.tvLabel.isVisible = b || autoShowLabel != false || time != null
        }

        binding.root.setOnClickListener {
            binding.editText.requestFocus()

            if (clockType == ClockType.Spinner) {
                showSpinner()
            } else {
                showClock()
            }
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TimeFieldView,
                0, 0
            )

            val lFormat = a.getString(R.styleable.TimeFieldView_timeFormat)
            if (lFormat != null) {
                format = lFormat
            }

            fieldEnabled = a.getBoolean(R.styleable.TimeFieldView_fieldEnabled, true)
            spinnerTitle = a.getString(R.styleable.TimeFieldView_spinnerTitle)
            spinnerButtonText = a.getString(R.styleable.TimeFieldView_spinnerButtonText)
            hint = a.getString(R.styleable.TimeFieldView_hint)
            label = a.getString(R.styleable.TimeFieldView_label)
            autoShowLabel = a.getBoolean(R.styleable.TimeFieldView_autoShowLabel, true)
            showIcon = a.getBoolean(R.styleable.TimeFieldView_showIcon, true)

            val clockTypeIndex = a.getInt(R.styleable.TimeFieldView_clockType, 0)
            clockType = ClockType.values()[clockTypeIndex]

            a.recycle()
        }
    }

    private fun showSpinner() {
        val binding = DsTimeFieldSpinnerBinding.inflate(LayoutInflater.from(context))
        binding.bvSubmit.text = spinnerButtonText

        selectedTime = if (time == null) LocalTime.now() else time!!

        binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
            val result = LocalTime.of(hour, minute)
            selectedTime = result
        }

        val dialog = (if (spinnerTitle == null) "" else spinnerTitle)?.let {
            BottomLayoutDialog.showTray(
                context = context,
                title = it, contentView = binding.root
            )
        }

        binding.bvSubmit.setOnClickListener {
            time = selectedTime
            dialog?.close()
        }


    }

    private fun showClock() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            val now = LocalTime.now()
            selectedTime = if (time == null) now else time!!

            val dialog = TimePickerDialog(
                context, R.style.TimePickerTheme,
                { _, hour, minute ->
                    val result = LocalTime.of(hour, minute)
                    this@TimeFieldView.time = result
                }, selectedTime!!.hour, selectedTime!!.minute, DateFormat.is24HourFormat(context)
            )

            dialog.show()
        } else {
            val binding = DsViewTimePickerBinding.inflate(LayoutInflater.from(context), null, false)

            binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
                val result = LocalTime.of(hour, minute)
                selectedTime = result
            }

            val builder = AlertDialog.Builder(context)
            builder.setView(binding.root)
            builder.setNegativeButton(android.R.string.cancel) { p0, _ ->
                p0.dismiss()
            }

            builder.setPositiveButton(
                android.R.string.ok
            ) { p0, _ ->
                this@TimeFieldView.time = selectedTime
                p0?.dismiss()
            }
            builder.show()
        }
    }
}