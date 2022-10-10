package id.co.edtslib.edtsds.checkbox

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import id.co.edtslib.edtsds.R

class CheckBox: AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var delegate: CheckBoxDelegate? = null

    init {
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checkbox, 0, 0, 0)
        gravity = Gravity.CENTER_VERTICAL
        if (text?.isNotEmpty() == true) {
            compoundDrawablePadding = context.resources.getDimensionPixelSize(R.dimen.dimen_checkbox_padding)
        }

        setOnClickListener {
            isActivated = ! isActivated
            delegate?.onChecked(isActivated)
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)

        if (text?.isNotEmpty() == true) {
            compoundDrawablePadding = context.resources.getDimensionPixelSize(R.dimen.dimen_checkbox_padding)
        }
    }
}