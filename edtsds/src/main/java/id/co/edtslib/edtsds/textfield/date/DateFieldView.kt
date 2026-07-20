package id.co.edtslib.edtsds.textfield.date

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.bottom.BottomLayoutDialog
import id.co.edtslib.edtsds.databinding.DsDateFieldSpinnerBinding
import id.co.edtslib.edtsds.databinding.DsDateFieldSpinnerWheelBinding
import id.co.edtslib.edtsds.databinding.DsViewDateFieldBinding
import id.co.edtslib.edtsds.databinding.ViewDatePickerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.core.graphics.drawable.toDrawable

open class DateFieldView : FrameLayout {
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

    enum class CalendarType {
        Calendar, Spinner, SpinnerWheel
    }

    companion object {
        private val MONTHS = arrayOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
    }

    private fun requireView() = this

    protected val binding =
        DsViewDateFieldBinding.inflate(LayoutInflater.from(context), requireView(), true)
    protected var selectedDate: Date? = null

    var fieldEnabled: Boolean = true
        set(value) {
            field = value
            binding.root.isEnabled = value
            binding.clContent.isEnabled = value
        }

    var delegate: DateFieldDelegate? = null

    var spinnerTitle: String? = null
    var spinnerButtonText: String? = null

    // Spinner selection band style.
    var spinnerBandColor: Int = ContextCompat.getColor(context, R.color.colorDatePickerBand)
    var spinnerBandRadius: Float = resources.getDimension(R.dimen.dimen_8dp)
    var spinnerBandHeight: Int =
        resources.getDimensionPixelSize(R.dimen.date_picker_band_height)

    // Spinner wheel background, drawn behind the rows other than the band.
    var spinnerBackgroundColor: Int = Color.TRANSPARENT
    var spinnerBackgroundRadius: Float = 0f

    // Spinner day/month/year wheel text style.
    var spinnerSelectedTextColor: Int =
        ContextCompat.getColor(context, R.color.colorDatePickerSelectedText)
    var spinnerUnselectedTextColor: Int =
        ContextCompat.getColor(context, R.color.colorDatePickerUnselectedText)

    /** Wheel text size in px; `0` keeps the [NumberPicker] default. */
    var spinnerTextSize: Float = 0f

    /**
     * TextAppearance style resource (e.g. `R.style.H1`) for the selected (center)
     * row. Drives text size and font; `0` means none. Color stays controlled by
     * [spinnerSelectedTextColor], and an explicit [spinnerTextSize] overrides the
     * size from this style.
     */
    var spinnerSelectedTextAppearance: Int = 0

    /**
     * TextAppearance style resource for the unselected (fading) rows. Drives text
     * size and font; `0` means none. Color stays controlled by
     * [spinnerUnselectedTextColor], and an explicit [spinnerTextSize] overrides
     * the size from this style.
     */
    var spinnerUnselectedTextAppearance: Int = 0

    var showIcon = true
        set(value) {
            field = value
            binding.imageView.isVisible = value
        }

    var icon: Int = 0
        set(value) {
            field = value
            binding.imageView.setImageResource(value)
        }

    open var calendarType = CalendarType.Calendar

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

    var minDate: Date? = null
    var maxDate: Date? = null

    open var format = "dd-MM-yyyy"

    var minAge = 0
        set(value) {
            field = value

            val now = Date()
            val calendar = Calendar.getInstance()
            calendar.time = now
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - minAge)

            maxDate = calendar.time
        }


    var enableFuture = false
        set(value) {
            field = value
            if (value) {
                maxDate = null
            }
        }

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
        binding.tvValue.text = if (date == null) hint else {
            val simpleDateFormat = SimpleDateFormat(format, Locale("ID"))
            simpleDateFormat.format(date!!)
        }
        binding.tvValue.isActivated = date != null
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

            when (calendarType) {
                CalendarType.Spinner -> showSpinner()
                CalendarType.SpinnerWheel -> showSpinnerWheel()
                else -> showCalendar()
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

            spinnerBandColor =
                a.getColor(R.styleable.DateFieldView_spinnerBandColor, spinnerBandColor)
            spinnerBandRadius =
                a.getDimension(R.styleable.DateFieldView_spinnerBandRadius, spinnerBandRadius)
            spinnerBandHeight = a.getDimensionPixelSize(
                R.styleable.DateFieldView_spinnerBandHeight, spinnerBandHeight
            )
            spinnerBackgroundColor = a.getColor(
                R.styleable.DateFieldView_spinnerBackgroundColor, spinnerBackgroundColor
            )
            spinnerBackgroundRadius = a.getDimension(
                R.styleable.DateFieldView_spinnerBackgroundRadius, spinnerBackgroundRadius
            )
            spinnerSelectedTextColor = a.getColor(
                R.styleable.DateFieldView_spinnerSelectedTextColor, spinnerSelectedTextColor
            )
            spinnerUnselectedTextColor = a.getColor(
                R.styleable.DateFieldView_spinnerUnselectedTextColor, spinnerUnselectedTextColor
            )
            spinnerTextSize =
                a.getDimension(R.styleable.DateFieldView_spinnerTextSize, spinnerTextSize)
            spinnerSelectedTextAppearance = a.getResourceId(
                R.styleable.DateFieldView_spinnerSelectedTextAppearance,
                spinnerSelectedTextAppearance
            )
            spinnerUnselectedTextAppearance = a.getResourceId(
                R.styleable.DateFieldView_spinnerUnselectedTextAppearance,
                spinnerUnselectedTextAppearance
            )
            icon = a.getResourceId(
                R.styleable.DateFieldView_dateIcon,
                R.drawable.ds_ic_date
            )

            val calendarTypeIndex = a.getInt(R.styleable.DateFieldView_calendarType, 0)
            calendarType = CalendarType.values()[calendarTypeIndex]

            a.recycle()
        } else {
            enableFuture = false
        }
    }

    private fun getMaxDate() = maxDate?.time

    protected open fun showSpinner() {
        val binding = DsDateFieldSpinnerBinding.inflate(LayoutInflater.from(context))
        binding.bvSubmit.text = spinnerButtonText
        if (maxDate != null) {
            binding.datePicker.maxDate = getMaxDate()!!
        }
        if (minDate != null) {
            binding.datePicker.minDate = minDate!!.time
        }

        selectedDate = if (date == null) Date() else date!!

        val calendar = Calendar.getInstance()
        calendar.time = if (selectedDate == null) Date() else selectedDate!!

        binding.datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)
        ) { _, year, month, date ->
            val result = Calendar.getInstance()
            result.set(Calendar.YEAR, year)
            result.set(Calendar.MONTH, month)
            result.set(Calendar.DATE, date)

            selectedDate = result.time
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

    /**
     * Custom wheel spinner (day / month name / year) with a stylable selection
     * band, wheel background and per-state text appearances. Separate from the
     * legacy [showSpinner] so the native DatePicker spinner stays untouched.
     */
    protected open fun showSpinnerWheel() {
        val binding = DsDateFieldSpinnerWheelBinding.inflate(LayoutInflater.from(context))
        binding.bvSubmit.text = spinnerButtonText

        selectedDate = if (date == null) Date() else date!!

        val calendar = Calendar.getInstance()
        calendar.time = selectedDate!!

        val minCal = minDate?.let { Calendar.getInstance().apply { time = it } }
        val maxCal = maxDate?.let { Calendar.getInstance().apply { time = it } }

        val pickerDay = binding.pickerDay
        val pickerMonth = binding.pickerMonth
        val pickerYear = binding.pickerYear

        // Year wheel bounds.
        val minYear = minCal?.get(Calendar.YEAR) ?: 1900
        val maxYear = maxCal?.get(Calendar.YEAR) ?: (calendar.get(Calendar.YEAR) + 100)
        pickerYear.wrapSelectorWheel = false
        pickerYear.minValue = minYear
        pickerYear.maxValue = maxYear
        pickerYear.value = calendar.get(Calendar.YEAR).coerceIn(minYear, maxYear)

        // Month wheel (full Indonesian month names).
        pickerMonth.wrapSelectorWheel = false
        pickerMonth.displayedValues = MONTHS
        pickerMonth.minValue = 0
        pickerMonth.maxValue = MONTHS.size - 1
        pickerMonth.value = calendar.get(Calendar.MONTH)

        // Day wheel is (re)configured against the current month/year.
        pickerDay.wrapSelectorWheel = false

        fun clampMonthRange() {
            val year = pickerYear.value
            val minMonth = if (minCal != null && year == minYear) minCal.get(Calendar.MONTH) else 0
            val maxMonth =
                if (maxCal != null && year == maxYear) maxCal.get(Calendar.MONTH) else MONTHS.size - 1
            pickerMonth.minValue = minMonth
            pickerMonth.maxValue = maxMonth
        }

        fun clampDayRange() {
            val year = pickerYear.value
            val month = pickerMonth.value
            val temp = Calendar.getInstance()
            temp.set(Calendar.YEAR, year)
            temp.set(Calendar.MONTH, month)
            var minDay = 1
            var maxDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH)
            if (minCal != null && year == minYear && month == minCal.get(Calendar.MONTH)) {
                minDay = minCal.get(Calendar.DATE)
            }
            if (maxCal != null && year == maxYear && month == maxCal.get(Calendar.MONTH)) {
                maxDay = maxCal.get(Calendar.DATE)
            }
            pickerDay.minValue = minDay
            pickerDay.maxValue = maxDay
        }

        fun updateSelectedDate() {
            val result = Calendar.getInstance()
            result.set(Calendar.YEAR, pickerYear.value)
            result.set(Calendar.MONTH, pickerMonth.value)
            result.set(Calendar.DATE, pickerDay.value)
            selectedDate = result.time
        }

        clampMonthRange()
        clampDayRange()
        // Set the day only after its min/max are configured, otherwise NumberPicker
        // clamps it to the default 0..0 range and the picked date is lost.
        pickerDay.value = calendar.get(Calendar.DATE)
            .coerceIn(pickerDay.minValue, pickerDay.maxValue)

        // Re-assert the selected style on the centered value of every wheel. Needed
        // because NumberPicker hides its center EditText on any touch (drag, fling
        // or tap-to-step) and does not reliably restore it, which would otherwise
        // leave the newly selected value drawn in the unselected wheel paint. Posted
        // so it runs after NumberPicker finishes its own value/scroll update.
        fun restyleSelected(source: NumberPicker) {
            source.post {
                listOf(pickerDay, pickerMonth, pickerYear).forEach {
                    applySelectedTextStyle(
                        it,
                        spinnerSelectedTextColor,
                        spinnerTextSize,
                        spinnerSelectedTextAppearance
                    )
                    it.invalidate()
                }
            }
        }

        pickerYear.setOnValueChangedListener { np, _, _ ->
            clampMonthRange()
            clampDayRange()
            updateSelectedDate()
            restyleSelected(np)
        }
        pickerMonth.setOnValueChangedListener { np, _, _ ->
            clampDayRange()
            updateSelectedDate()
            restyleSelected(np)
        }
        pickerDay.setOnValueChangedListener { np, _, _ ->
            updateSelectedDate()
            restyleSelected(np)
        }

        binding.flWheels.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(spinnerBackgroundColor)
            cornerRadius = spinnerBackgroundRadius
        }

        binding.vBand.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(spinnerBandColor)
            cornerRadius = spinnerBandRadius
        }
        binding.vBand.layoutParams = binding.vBand.layoutParams.apply {
            height = spinnerBandHeight
        }

        listOf(pickerDay, pickerMonth, pickerYear).forEach { picker ->
            styleNumberPicker(
                picker,
                spinnerSelectedTextColor,
                spinnerUnselectedTextColor,
                spinnerTextSize,
                spinnerSelectedTextAppearance,
                spinnerUnselectedTextAppearance
            )
            // NumberPicker hides the center EditText on touch and only shows it
            // again when idle; re-apply the selected style then so the newly
            // centered value doesn't stay in the unselected appearance.
            picker.setOnScrollListener { np, state ->
                if (state == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    applySelectedTextStyle(
                        np,
                        spinnerSelectedTextColor,
                        spinnerTextSize,
                        spinnerSelectedTextAppearance
                    )
                    np.invalidate()
                }
            }
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

    /**
     * Styles a wheel to match the design: near-black bold selected text, gray
     * unselected text, and no built-in divider lines (the light-blue selection
     * band is drawn by the layout behind the wheels). The color/typeface knobs
     * are only reachable through reflection on the [NumberPicker] internals, so
     * every step is wrapped and degrades gracefully on OEM ROMs / newer APIs.
     */
    private fun styleNumberPicker(
        picker: NumberPicker,
        selectedColor: Int,
        unselectedColor: Int,
        textSize: Float,
        selectedAppearance: Int,
        unselectedAppearance: Int
    ) {
        val transparent = ContextCompat.getColor(context, android.R.color.transparent)

        // Hide the native divider lines so only the layout's band shows.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                picker.selectionDividerHeight = 0
            } catch (_: Exception) {
            }
        }
        try {
            NumberPicker::class.java.getDeclaredField("mSelectionDivider").apply {
                isAccessible = true
                set(picker, transparent.toDrawable())
            }
        } catch (_: Exception) {
        }

        // Selected (center) text, drawn by the inner EditText.
        applySelectedTextStyle(picker, selectedColor, textSize, selectedAppearance)

        // Unselected (fading) text, drawn by the wheel paint. Its size + font come
        // from its own TextAppearance (resolved through a throwaway TextView), but
        // the color stays from the attribute.
        val unselected = resolveAppearance(unselectedAppearance)
        try {
            NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint").apply {
                isAccessible = true
                (get(picker) as? Paint)?.apply {
                    color = unselectedColor
                    typeface = unselected?.typeface ?: Typeface.DEFAULT_BOLD
                    when {
                        textSize > 0f -> this.textSize = textSize
                        unselected != null -> this.textSize = unselected.sizePx
                    }
                }
            }
        } catch (_: Exception) {
        }

        // Public API (API 29+) reinforces the wheel color where available.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                picker.textColor = unselectedColor
            } catch (_: Exception) {
            }
        }

        picker.invalidate()
    }

    /**
     * Applies the selected (center) style to the wheel's inner EditText and forces
     * it back to VISIBLE. [NumberPicker] hides that EditText on touch and only
     * restores it when idle, so the centered value must be re-styled and re-shown
     * on scroll settle — otherwise the center keeps rendering with the unselected
     * wheel paint. Color stays from the attribute to preserve the two-tone look.
     */
    private fun applySelectedTextStyle(
        picker: NumberPicker,
        selectedColor: Int,
        textSize: Float,
        selectedAppearance: Int
    ) {
        try {
            NumberPicker::class.java.getDeclaredField("mInputText").apply {
                isAccessible = true
                (get(picker) as? EditText)?.apply {
                    visibility = View.VISIBLE
                    if (selectedAppearance != 0) {
                        TextViewCompat.setTextAppearance(this, selectedAppearance)
                    } else {
                        typeface = Typeface.DEFAULT_BOLD
                    }
                    setTextColor(selectedColor)
                    if (textSize > 0f) {
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    }
                }
            }
        } catch (_: Exception) {
        }
    }

    private data class ResolvedTextAppearance(val typeface: Typeface?, val sizePx: Float)

    /**
     * Resolves the typeface and text size of a TextAppearance [styleRes] by
     * applying it to a throwaway [TextView], so the wheel paint (which has no
     * TextAppearance API) can mirror it. Returns `null` when [styleRes] is 0.
     */
    private fun resolveAppearance(styleRes: Int): ResolvedTextAppearance? {
        if (styleRes == 0) return null
        val probe = TextView(context)
        TextViewCompat.setTextAppearance(probe, styleRes)
        return ResolvedTextAppearance(probe.typeface, probe.textSize)
    }

    protected open fun showCalendar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            val calendar = Calendar.getInstance()
            calendar.time = if (date == null) Date() else date!!
            selectedDate = calendar.time

            val dialog = DatePickerDialog(
                context, R.style.CalendarDatePickerDialog,
                { _, year, month, date ->
                    val result = Calendar.getInstance()
                    result.set(Calendar.YEAR, year)
                    result.set(Calendar.MONTH, month)
                    result.set(Calendar.DATE, date)

                    this@DateFieldView.date = result.time
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)
            )

            if (maxDate != null) {
                dialog.datePicker.maxDate = getMaxDate()!!
            }
            if (minDate != null) {
                dialog.datePicker.minDate = minDate!!.time
            }
            dialog.show()
        } else {
            val binding = ViewDatePickerBinding.inflate(LayoutInflater.from(context), null, false)
            if (maxDate != null) {
                binding.datePicker.maxDate = getMaxDate()!!
            }
            if (minDate != null) {
                binding.datePicker.minDate = minDate!!.time
            }

            val calendar = Calendar.getInstance()
            calendar.time = if (date == null) Date() else date!!

            binding.datePicker.init(
                calendar.get(Calendar.YEAR),
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
            builder.setNegativeButton(android.R.string.cancel) { p0, _ ->
                p0.dismiss()
            }

            builder.setPositiveButton(
                android.R.string.ok
            ) { p0, _ ->
                this@DateFieldView.date = selectedDate
                p0?.dismiss()
            }
            builder.show()
        }
    }
}