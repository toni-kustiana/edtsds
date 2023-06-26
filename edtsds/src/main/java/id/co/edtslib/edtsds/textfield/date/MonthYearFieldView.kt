package id.co.edtslib.edtsds.textfield.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.databinding.DsViewMonthYearFieldBinding
import java.util.Calendar
import java.util.Date

class MonthYearFieldView : DateFieldView {

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

    companion object {
        val MONTHS = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "Mei",
            "Jun",
            "Jul",
            "Agu",
            "Sep",
            "Okt",
            "Nov",
            "Des"
        )
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
            binding.tvValue.isActivated = date != null

            binding.tvLabel.isVisible = b || autoShowLabel != false || date != null
        }

        binding.root.setOnClickListener {
            binding.editText.requestFocus()

            if (calendarType == CalendarType.Spinner) {
                showSpinner()
            } else {
                showCalendar()
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

            fieldEnabled = a.getBoolean(R.styleable.DateFieldView_fieldEnabled, true)
            spinnerTitle = a.getString(R.styleable.DateFieldView_spinnerTitle)
            spinnerButtonText = a.getString(R.styleable.DateFieldView_spinnerButtonText)
            hint = a.getString(R.styleable.DateFieldView_hint)
            label = a.getString(R.styleable.DateFieldView_label)
            autoShowLabel = a.getBoolean(R.styleable.DateFieldView_autoShowLabel, true)
            showIcon = a.getBoolean(R.styleable.DateFieldView_showIcon, true)

            enableFuture = a.getBoolean(R.styleable.DateFieldView_enableFuture, false)

            val calendarTypeIndex = a.getInt(R.styleable.DateFieldView_calendarType, 0)
            calendarType = CalendarType.values()[calendarTypeIndex]

            a.recycle()
        }
        else {
            enableFuture = false
        }

        // new added code. special for month year picker
        calendarType = CalendarType.Spinner
        format = "MMM yyyy"
    }

    override fun showSpinner() {
        val binding = DsViewMonthYearFieldBinding.inflate(LayoutInflater.from(context))
        binding.bvSubmit.text = spinnerButtonText
        binding.pickerYear.wrapSelectorWheel = false
        binding.pickerMonth.wrapSelectorWheel = false
        binding.pickerMonth.displayedValues = MONTHS
        if (maxDate != null) {
            val cal = Calendar.getInstance()
            cal.time = maxDate!!
            val maxYear = cal[Calendar.YEAR]
            val maxMonth = cal[Calendar.MONTH]
            binding.pickerYear.minValue = 1970
            binding.pickerYear.maxValue = maxYear
            binding.pickerMonth.minValue = 0
            if (binding.pickerYear.value == maxYear) {
                binding.pickerMonth.maxValue = maxMonth
            } else {
                binding.pickerMonth.maxValue = 11
            }
        }
        if (minDate != null) {
            val cal = Calendar.getInstance()
            cal.time = maxDate!!
            val minYear = cal[Calendar.YEAR]
            val minMonth = cal[Calendar.MONTH]
            binding.pickerYear.minValue = minYear
            binding.pickerMonth.maxValue = 11
            if (binding.pickerYear.value == minYear) {
                binding.pickerMonth.minValue = minMonth
            } else {
                binding.pickerMonth.minValue = 0
            }
        }

        selectedDate = if (date == null) Date() else date!!

        val calendar = Calendar.getInstance()
        calendar.time = if (selectedDate == null) Date() else selectedDate!!
        calendar.set(Calendar.DATE, 1)

        binding.pickerMonth.value = calendar.get(Calendar.MONTH)
        binding.pickerYear.value = calendar.get(Calendar.YEAR)

        binding.pickerMonth.setOnValueChangedListener { _, oldMonth, newMonth ->
            if (newMonth != oldMonth) {
                calendar.set(Calendar.MONTH, newMonth)
            }
            selectedDate = calendar.time
        }
        binding.pickerYear.setOnValueChangedListener { _, oldYear, newYear ->
            if (newYear != oldYear) {
                calendar.set(Calendar.YEAR, newYear)
            }
            selectedDate = calendar.time
        }

        val dialog = (if (spinnerTitle == null) "" else spinnerTitle)?.let {
            BottomLayoutDialog.showTray(
                context = context,
                title = it, contentView = binding.root
            )
        }

        binding.bvSubmit.setOnClickListener {
            date = selectedDate
            dialog?.close()
        }
    }

}