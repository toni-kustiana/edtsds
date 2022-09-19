package id.co.edtslib

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

class ButtonView: AppCompatTextView {
    enum class ButtonState {
        Medium, Small
    }

    enum class ButtonMode {
        Light, Dark
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var state: ButtonState = ButtonState.Medium
        set(value) {
            field = value

            val dp12 = resources.getDimensionPixelSize(R.dimen.dimen_12dp)
            val left = if (value == ButtonState.Small) resources.getDimensionPixelSize(R.dimen.dimen_12dp)
                else resources.getDimensionPixelSize(R.dimen.dimen_16dp)

            setPadding(left, dp12, left, dp12)

            TextViewCompat.setTextAppearance(this, if (value == ButtonState.Small) R.style.H4
                else R.style.H2)
        }

    var mode: ButtonMode = ButtonMode.Dark
        set(value) {
            field = value

            setTextColor(ContextCompat.getColor(context, if (value == ButtonMode.Light) R.color.colorNeutral70
                else R.color.colorWhite))
        }

    init {
        state = ButtonState.Medium
        mode = ButtonMode.Dark

        setBackgroundResource(R.drawable.bg_button)
        gravity = Gravity.CENTER_HORIZONTAL
    }
}