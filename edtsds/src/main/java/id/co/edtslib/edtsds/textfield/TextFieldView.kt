package id.co.edtslib.edtsds.textfield

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.base.MyMaskedTextChangedListener
import id.co.edtslib.edtsds.base.MyValueListener
import java.text.DecimalFormat

class TextFieldView: TextInputLayout {
    enum class InputType {
        Text, Password, Pin, Phone, Ktp, Address, Search, Email, Popup, Money, Number
    }

    enum class ImeOption {
        Next, Done, Send, Search
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

    private var hint: String? = null
    private var emptyHint: String? = null
        set(value) {
            field = value
            if (value != null) {
                placeholderText = emptyHint
            }
        }

    private var ellipsizeWidth = 0
    private var startIcon: Int = 0
    private var ellipsize = false

    var text: String? = null
        set(value) {
            field = value

            if (inputType == InputType.Search) {
                isHintEnabled = value?.isNotEmpty() != true
            }

            if (ellipsize && value != null) {
                editText?.post {
                    val diff = if (ellipsizeWidth == 0) context.resources.getDimensionPixelSize(R.dimen.dimen_72dp) else ellipsizeWidth
                    val width = editText!!.width - diff

                    val bounds = Rect()
                    editText!!.paint.getTextBounds(value, 0, value.length, bounds)
                    if (bounds.width() < width) {
                        editText?.setText(value)
                    }
                    else {
                        var i = 1
                        while(true) {
                            val temp = "${value.substring(0, value.length-i)}..."
                            editText!!.paint.getTextBounds(temp, 0, temp.length, bounds)
                            if (bounds.width() < width) {
                                editText?.setText(temp)
                                break
                            }

                            i++
                        }
                    }
                }
            }
            else {
                editText?.setText(value)
            }
        }
        get() = editText?.text?.toString()

    var delegate: TextFieldDelegate? = null

    var imeOption = ImeOption.Next
        set(value) {
            field = value
            when(value) {
                ImeOption.Done -> {
                    editText?.imeOptions = EditorInfo.IME_ACTION_DONE
                }
                ImeOption.Send -> {
                    editText?.imeOptions = EditorInfo.IME_ACTION_SEND
                }
                ImeOption.Search -> {
                    editText?.imeOptions = EditorInfo.IME_ACTION_SEARCH
                }
                else -> {
                    editText?.imeOptions = EditorInfo.IME_ACTION_NEXT
                }
            }

        }

    var currency: String? = null
    var maxLength = 0
        set(value) {
            field = value
            if (value > 0) {
                addFilter(InputFilter.LengthFilter(maxLength))
            }
            else {
                removeMaxLengthFilter()
            }
        }

    var inputType = InputType.Text
        set(value) {
            field = value


            when(value) {
                InputType.Password -> {
                    endIconMode = END_ICON_PASSWORD_TOGGLE
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }
                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
                InputType.Pin -> {
                    endIconMode = END_ICON_PASSWORD_TOGGLE
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }
                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
                InputType.Phone -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_PHONE or
                        android.text.InputType.TYPE_TEXT_VARIATION_PHONETIC

                    setPhoneListener()
                }
                InputType.Money -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                            android.text.InputType.TYPE_TEXT_VARIATION_NORMAL
                    prefixText = currency

                    setMoneyListener()
                }
                InputType.Number -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                            android.text.InputType.TYPE_TEXT_VARIATION_NORMAL

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }

                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
                InputType.Email -> {
                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }

                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
                InputType.Ktp -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_PHONE or
                            android.text.InputType.TYPE_TEXT_VARIATION_PHONETIC

                    setKtpListener()
                }
                InputType.Address -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }
                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
                InputType.Popup -> {
                    endIconMode = END_ICON_CUSTOM
                    endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_chevron_down, null)
                    editText?.inputType = android.text.InputType.TYPE_NULL
                    editText?.setOnFocusChangeListener { v, b ->
                        if (b) {
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                            imm?.hideSoftInputFromWindow(v.windowToken, 0)

                            editText?.performClick()
                            delegate?.onChanged(editText?.text?.toString())

                            editText?.setOnClickListener {
                                delegate?.onChanged(editText?.text?.toString())
                            }
                        }
                        else {
                            editText?.setOnClickListener(null)
                        }
                    }
                }
                InputType.Search -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_VARIATION_NORMAL

                    editText?.addTextChangedListener {
                        endIconMode = if (it?.toString()?.isNotEmpty() == true) END_ICON_CUSTOM else END_ICON_NONE
                        endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_search_cancel,
                            null)
                        setEndIconOnClickListener {
                            editText?.text = null
                        }

                        delegate?.onChanged(it?.toString())
                    }

                    editText?.setOnFocusChangeListener { _, b ->
                        isHintEnabled = ! b && text?.isNotEmpty() != true
                    }

                    startIconDrawable = if (startIcon == 0) {
                        ResourcesCompat.getDrawable(
                            resources, R.drawable.ic_search,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(
                            resources, startIcon,
                            null
                        )
                    }
                }
                else -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_NORMAL

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }

                    editText?.setOnFocusChangeListener { _, b ->
                        setHintOnFocus(b)
                    }
                }
            }
        }

    private fun addFilter(inputFilter: InputFilter) {
        val filters = editText?.filters?.toMutableList()
        if (filters == null) {
            editText?.filters = arrayOf(inputFilter)
        }
        else {
            filters.add(inputFilter)
            editText?.filters = filters.toTypedArray()
        }
    }

    private fun removeMaxLengthFilter() {
        val filters = editText?.filters?.toMutableList()
        if (filters != null) {
            for (item in filters) {
                if (item is InputFilter.LengthFilter) {
                    filters.remove(item)
                    editText?.filters = filters.toTypedArray()
                    return
                }
            }
        }
    }

    private fun init(attrs: AttributeSet?) {
        val editText = TextInputEditText(context)
        addView(editText)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TextFieldView,
                0, 0
            )

            ellipsize = a.getBoolean(R.styleable.TextFieldView_ellipsize, false)
            ellipsizeWidth = a.getDimension(R.styleable.TextFieldView_ellipsizeWidth, 0f).toInt()

            emptyHint = a.getString(R.styleable.TextFieldView_emptyHint)
            currency = a.getString(R.styleable.TextFieldView_currency)

            startIcon = a.getResourceId(R.styleable.TextFieldView_startIcon, 0)

            val v = a.getInt(R.styleable.TextFieldView_inputType, 0)
            inputType = InputType.values()[v]

            maxLength = a.getInt(R.styleable.TextFieldView_maxLength, 0)

            val e = a.getInt(R.styleable.TextFieldView_imeOptions, 0)
            imeOption = ImeOption.values()[e]

            a.recycle()
        }
        else {
            inputType = InputType.Text
            imeOption = ImeOption.Next
        }

        setup(editText)
    }

    private fun setHintOnFocus(b: Boolean) {
        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
            val h = if (b) hint else emptyHint
            if (h != null) {
                this@TextFieldView.setHint(HtmlCompat.fromHtml(h, HtmlCompat.FROM_HTML_MODE_LEGACY))
            }
            else {
                this@TextFieldView.setHint(null)
            }
        }
    }

    private fun setup(editText: TextInputEditText) {
        hint = editText.hint?.toString()
        if (emptyHint?.isNotEmpty() == true) {
            this@TextFieldView.setHint(emptyHint)
        }

        val layoutParams = editText.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        if (! isHintEnabled) {
            val prefixView = findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_prefix_text)
            prefixView.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            prefixView.gravity = Gravity.CENTER

            editText.setPadding(editText.paddingStart, 0, editText.paddingEnd, 0)

            post {
                layoutParams.height = height
                editText.hint = emptyHint
            }
        }
        else
        if (inputType == InputType.Search) {
            editText.setPadding(0, 0, 0, 0)
        }
        else {
            val dp4 = resources.getDimensionPixelSize(R.dimen.dimen_4dp)

            editText.setPadding(
                editText.paddingStart,
                editText.paddingTop + dp4,
                editText.paddingEnd,
                editText.paddingBottom
            )
        }

        TextViewCompat.setTextAppearance(editText, R.style.B1)
        editText.setTextColor(ContextCompat.getColorStateList(context,
            R.color.color_text_text_field))
        if (isHintEnabled) {
            editText.setBackgroundResource(
                if (inputType == InputType.Search) R.drawable.bg_search_field
                else R.drawable.bg_text_field
            )
        }
        else {
            editText.setBackgroundResource(0)
            setBackgroundResource(R.drawable.bg_text_field)
        }

        setErrorTextAppearance(R.style.B3)
        setErrorTextColor((ContextCompat.getColorStateList(context, R.color.colorSupportError)))

        setHelperTextTextAppearance(R.style.B3)
        setHelperTextColor((ContextCompat.getColorStateList(context, R.color.colorNeutral50)))

        setPrefixTextAppearance(R.style.B1)
        setPrefixTextColor(ContextCompat.getColorStateList(context, R.color.colorNeutral70)!!)

        placeholderTextAppearance = R.style.B1
        placeholderTextColor = ContextCompat.getColorStateList(context, R.color.colorNeutral40)

        boxBackgroundMode = BOX_BACKGROUND_FILLED

        boxStrokeWidth = 0
        boxStrokeWidthFocused = 0

        setHintTextAppearance(R.style.H3)
        defaultHintTextColor = ContextCompat.getColorStateList(context, R.color.colorNeutral40)
        hintTextColor = ContextCompat.getColorStateList(context, R.color.color_hint_text_field)

        setIndicatorPadding()
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        isSelected = errorText != null

        setIndicatorPadding()
    }

    private fun setIndicatorPadding() {
        if (childCount > 1) {
            val dp4 = resources.getDimensionPixelSize(R.dimen.dimen_4dp)

            val view = getChildAt(1)
            view.setPadding(0, dp4, 0, 0)
        }
    }

    private fun setMoneyListener() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val s = p0?.toString()
                val n = s?.replace(".", "")

                val len = n?.length ?: 0
                val latestSelection = editText?.selectionEnd
                val isLast = editText?.selectionEnd == p0?.length

                if (len < maxLength) {
                    delegate?.onChanged(n)
                    editText?.removeTextChangedListener(this)

                    try {
                        if (n?.toDouble() != null) {
                            val sn = formatDecimal(n.toDouble())!!

                            editText?.setText(sn)
                            if (isLast) {
                                editText?.setSelection(sn.length)
                            }
                            else
                            if (latestSelection != null) {
                                editText?.setSelection(latestSelection)
                            }
                        }
                    }
                    catch (ignore: NumberFormatException) {

                    }

                    editText?.addTextChangedListener(this)
                }
            }
        }

        editText?.addTextChangedListener(textWatcher)
    }

    private fun formatDecimal(d: Double): String? {
        val formatter = DecimalFormat("#,###")
        val symbols = formatter.decimalFormatSymbols
        symbols.groupingSeparator = '.'
        symbols.decimalSeparator = ','
        formatter.decimalFormatSymbols = symbols
        return formatter.format(d)
    }

    private fun setPhoneListener() {
        if (editText != null) {
            val listener = MyMaskedTextChangedListener("[00]-[0000]-[000000]",
                editText!!,
                object : MyValueListener {
                    override fun onFocussed(view: View?, hasFocus: Boolean) {
                        setHintOnFocus(hasFocus)
                    }

                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {

                        delegate?.onChanged("08$extractedValue")
                    }

                })

            editText!!.addTextChangedListener(listener)
            editText!!.onFocusChangeListener = listener

            prefixText = "08"
        }
    }

    private fun setKtpListener() {
        if (editText != null) {
            val listener = MyMaskedTextChangedListener("[0000].[0000].[0000].[0000]",
                editText!!,
                object : MyValueListener {
                    override fun onFocussed(view: View?, hasFocus: Boolean) {
                        setHintOnFocus(hasFocus)
                    }

                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {

                        delegate?.onChanged(extractedValue)
                    }

                })

            editText!!.addTextChangedListener(listener)
            editText!!.onFocusChangeListener = listener
        }
    }
}