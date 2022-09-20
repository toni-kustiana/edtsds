package id.co.edtslib.edtsds

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextFieldView: TextInputLayout {
    enum class InputType {
        Text, Password, Pin, Phone
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

                }
                InputType.Pin -> {
                    endIconMode = END_ICON_PASSWORD_TOGGLE
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
                }
                InputType.Phone -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                            android.text.InputType.TYPE_TEXT_VARIATION_PHONETIC
                    maxLength = 14
                }
                else -> {
                    editText?.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_NORMAL
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

            a.recycle()
        }
        else {
            inputType = InputType.Text
        }
    }


    private fun setup() {
        val editText = TextInputEditText(context)
        addView(editText)

        val layoutParams = editText.layoutParams
        layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

        editText.setTextAppearance(R.style.B1)
        editText.setTextColor(ContextCompat.getColor(context, R.color.colorNeutral70))
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
        hintTextColor = ContextCompat.getColorStateList(context, R.color.colorNeutral50)

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
}