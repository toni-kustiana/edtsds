package id.co.edtslib.edtsds.textfield.sex

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewSexFieldBinding

class SexFieldView: FrameLayout {
    enum class Sex {
        Man, Woman
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

    private val binding = ViewSexFieldBinding.inflate(LayoutInflater.from(context), this, true)

    var delegate: SexFieldDelegate? = null
    var error: String? = null
        set(value) {
            field = value
            binding.tvError.isVisible = value?.isNotEmpty() == true
            binding.tvError.text = error
        }

    var label: String? = null
        set(value) {
            field = value

            binding.tvLabel.text = label
        }

    var sex: Sex? = null
        set(value) {
            field = value

            binding.cbMan.isActivated = value == Sex.Man
            binding.cbWoman.isActivated = value == Sex.Woman

            delegate?.onSelected(value)
        }


    private fun init(attrs: AttributeSet?) {
        binding.tvError.isVisible = false

        binding.editText.setOnFocusChangeListener { view, b ->
            if (b) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        binding.cbMan.setOnClickListener {
            sex = Sex.Man
            binding.editText.requestFocus()
        }

        binding.cbWoman.setOnClickListener {
            sex = Sex.Woman
            binding.editText.requestFocus()
        }

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SexFieldView,
                0, 0
            )

            label = a.getString(R.styleable.SexFieldView_label)
            val iSex = a.getInt(R.styleable.SexFieldView_sex, -1)
            if (iSex >= 0) {
                sex = Sex.values()[iSex]
            }

            a.recycle()
        }
    }
}