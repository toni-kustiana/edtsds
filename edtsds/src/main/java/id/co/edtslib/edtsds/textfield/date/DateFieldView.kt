package id.co.edtslib.edtsds.textfield.date

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewDateFieldBinding
import id.co.edtslib.edtsds.databinding.ViewDatePickerBinding
import java.text.SimpleDateFormat
import java.util.*

class DateFieldView: FrameLayout {
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

    private val binding = ViewDateFieldBinding.inflate(LayoutInflater.from(context), this, true)
    private var selectedDate: Date? = null

    var delegate: DateFieldDelegate? = null

    var date: Date? = null
        set(value) {
            field = value
            setTextValue()

            if (value != null) {
                val simpleDateFormat = SimpleDateFormat(format, Locale("ID"))
                delegate?.onDateChanged(value, simpleDateFormat.format(value))
            }

            binding.tvLabel.isVisible = autoShowLabel != false || value != null
        }

    var format = "dd-MM-yyyy"
    var minAge = 0

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
        }



    private fun setTextValue() {
        binding.tvValue.text = if (date == null) hint else {
            val simpleDateFormat = SimpleDateFormat(format, Locale("ID"))
            simpleDateFormat.format(date!!)
        }
        binding.tvValue.isActivated = date != null
    }

    private fun init(attrs: AttributeSet?) {
        error = null

        binding.editText.setOnFocusChangeListener { _, b ->
            isActivated = b
            binding.tvValue.isActivated = date != null

            binding.tvLabel.isVisible = b || autoShowLabel != false || date != null
        }

        binding.root.setOnClickListener {
            binding.editText.requestFocus()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val calendar = Calendar.getInstance()
                calendar.time = if (date == null) Date() else date!!
                selectedDate = calendar.time

                val dialog = DatePickerDialog(context, R.style.CalendarDatePickerDialog,
                        { _, year, month, date ->
                            val result = Calendar.getInstance()
                            result.set(Calendar.YEAR, year)
                            result.set(Calendar.MONTH, month)
                            result.set(Calendar.DATE, date)

                            this@DateFieldView.date = result.time
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))

                dialog.datePicker.maxDate = getMaxDate()
                dialog.show()
            }
            else {
                val binding = ViewDatePickerBinding.inflate(LayoutInflater.from(context), null, false)
                binding.datePicker.maxDate = getMaxDate()

                val calendar = Calendar.getInstance()
                calendar.time = if (date == null) Date() else date!!

                binding.datePicker.init(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)
                ) { _, year, month, date ->
                    val result = Calendar.getInstance()
                    result.set(Calendar.YEAR, year)
                    result.set(Calendar.MONTH, month)
                    result.set(Calendar.DATE, date)

                    selectedDate = result.time
                }

                val builder = AlertDialog.Builder(context)
                builder.setView(binding.root)
                builder.setNegativeButton(android.R.string.cancel) {
                        p0, _ -> p0.dismiss()
                }

                builder.setPositiveButton(android.R.string.ok
                ) { p0, _ ->
                    this@DateFieldView.date = selectedDate
                    p0?.dismiss()
                }
                builder.show()
            }
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.DateFieldView,
                0, 0
            )

            minAge = a.getInteger(R.styleable.DateFieldView_minAge, 0)
            val lFormat = a.getString(R.styleable.DateFieldView_dateFormat)
            if (lFormat != null) {
                format = lFormat
            }

            hint = a.getString(R.styleable.DateFieldView_hint)
            label = a.getString(R.styleable.DateFieldView_label)
            autoShowLabel = a.getBoolean(R.styleable.DateFieldView_autoShowLabel, true)

            a.recycle()
        }
    }

    private fun getMaxDate(): Long {
        val now = Calendar.getInstance()
        now.time = Date()
        now.set(Calendar.YEAR, now.get(Calendar.YEAR)-minAge)

        return now.time.time
    }
}