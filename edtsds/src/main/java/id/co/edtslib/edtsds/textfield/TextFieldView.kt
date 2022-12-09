package id.co.edtslib.edtsds.textfield

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.base.MyMaskedTextChangedListener
import id.co.edtslib.edtsds.base.MyValueListener

class TextFieldView: TextInputLayout {
    enum class InputType {
        Text, Password, Pin, Phone, Ktp, Address, Search, Email, Popup
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

    private var startIcon: Int = 0

    var text: String? = null
        set(value) {
            field = value
            editText?.setText(text)
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
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (b) hint else emptyHint)
                        }
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
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (b) hint else emptyHint)
                        }
                    }
                }
                InputType.Phone -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_PHONE or
                        android.text.InputType.TYPE_TEXT_VARIATION_PHONETIC

                    setPhoneListener()
                }
                InputType.Email -> {
                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }

                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                    editText?.setOnFocusChangeListener { _, b ->
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (b) hint else emptyHint)
                        }
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
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (b) hint else emptyHint)
                        }
                    }
                }
                InputType.Popup -> {
                    endIconMode = END_ICON_CUSTOM
                    endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_chevron_right, null)
                    editText?.inputType = android.text.InputType.TYPE_NULL
                    editText?.setOnFocusChangeListener { v, b ->
                        if (b) {
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                            imm?.hideSoftInputFromWindow(v.windowToken, 0)

                            editText?.performClick()
                            delegate?.onChanged(editText?.text?.toString())
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
                        isHintEnabled = ! b
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
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (b) hint else emptyHint)
                        }
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

            emptyHint = a.getString(R.styleable.TextFieldView_emptyHint)

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


    private fun setup(editText: TextInputEditText) {
        hint = editText.hint?.toString()
        if (emptyHint?.isNotEmpty() == true) {
            this@TextFieldView.setHint(emptyHint)
        }

        val layoutParams = editText.layoutParams
        layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

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
        editText.setBackgroundResource(if (inputType == InputType.Search) R.drawable.bg_search_field
            else R.drawable.bg_text_field)

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

    private fun setPhoneListener() {
        if (editText != null) {
            val listener = MyMaskedTextChangedListener("[00]-[0000]-[000000]",
                editText!!,
                object : MyValueListener {
                    override fun onFocussed(view: View?, hasFocus: Boolean) {
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (hasFocus) hint else emptyHint)
                        }
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
                        if (emptyHint?.isNotEmpty() == true && editText?.text?.isNotEmpty() != true) {
                            this@TextFieldView.setHint(if (hasFocus) hint else emptyHint)
                        }
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