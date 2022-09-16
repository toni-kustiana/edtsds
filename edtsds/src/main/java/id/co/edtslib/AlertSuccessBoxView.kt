package id.co.edtslib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.databinding.ViewAlertSuccessBoxBinding

class AlertSuccessBoxView: FrameLayout {
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

    private val binding: ViewAlertSuccessBoxBinding =
        ViewAlertSuccessBoxBinding.inflate(LayoutInflater.from(context), this, true)


    var message: String? = null
        set(value) {
            field = value

            binding.textView.text = value
        }

    var dismissible: Boolean? = null
        set(value) {
            field = value

            binding.ivCancel.isVisible = value == true
        }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.AlertSuccessBoxView,
                0, 0
            )

            message = a.getString(R.styleable.AlertSuccessBoxView_message)
            dismissible = a.getBoolean(R.styleable.AlertSuccessBoxView_dismissible, true)

            a.recycle()
        }

        binding.ivCancel.setOnClickListener {
            isVisible = false
        }
    }

}