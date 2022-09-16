package id.co.edtslib

import android.content.Context
import android.util.AttributeSet

class AlertWarningBoxView: AlertSuccessBoxView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        binding.clContent.setBackgroundResource(R.drawable.bg_support_warning)
        binding.imageView.setImageResource(R.drawable.ic_alert_warning)

    }

}