package id.co.edtslib.edtsds.textfield

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.base.MyMaskedTextChangedListener

class TextFieldView: TextInputLayout {
    enum class InputType {
        Text, Password, Pin, Phone, Ktp, Address
    }

    enum class ImeOption {
        Next, Done, Send
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
                }
                InputType.Pin -> {
                    endIconMode = END_ICON_PASSWORD_TOGGLE
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
                    }
                }
                InputType.Phone -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_PHONE or
                        android.text.InputType.TYPE_TEXT_VARIATION_PHONETIC

                    setPhoneListener()
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
                }
                else -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_NORMAL

                    editText?.addTextChangedListener {
                        delegate?.onChanged(it?.toString())
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
        setup()

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TextFieldView,
                0, 0
            )

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
    }


    private fun setup() {
        val editText = TextInputEditText(context)
        addView(editText)

        val layoutParams = editText.layoutParams
        layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

        val dp4 = resources.getDimensionPixelSize(R.dimen.dimen_4dp)

        editText.setPadding(editText.paddingStart, editText.paddingTop+dp4, editText.paddingEnd, editText.paddingBottom)

        TextViewCompat.setTextAppearance(editText, R.style.B1)
        editText.setTextColor(ContextCompat.getColorStateList(context,
            R.color.color_text_text_field))
        editText.setBackgroundResource(R.drawable.bg_text_field)

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

        errorIconDrawable = null
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
                object : MaskedTextChangedListener.ValueListener {
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
                object : MaskedTextChangedListener.ValueListener {
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