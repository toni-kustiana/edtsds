package id.co.edtslib.alertbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import id.co.edtslib.R
import id.co.edtslib.databinding.ViewAlertSuccessBoxBinding

open class AlertBoxView: FrameLayout {
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

    enum class AlertType {
        Success, Warning, Error
    }

    private val binding: ViewAlertSuccessBoxBinding =
        ViewAlertSuccessBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var delegate: AlertBoxDelegate? = null

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

    var type = AlertType.Success
        set(value) {
            field = value

            binding.root.isSelected = type == AlertType.Error || type == AlertType.Warning
            binding.root.isActivated = type == AlertType.Error
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

            val typeIdx = a.getInt(R.styleable.AlertSuccessBoxView_type, AlertType.Success.ordinal)
            type = AlertType.values()[typeIdx]

            a.recycle()
        }

        binding.ivCancel.setOnClickListener {
            isVisible = false
            delegate?.onDismiss()
        }
    }

}