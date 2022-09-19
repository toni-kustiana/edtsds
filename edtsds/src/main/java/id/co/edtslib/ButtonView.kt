package id.co.edtslib

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

class ButtonView: AppCompatTextView {
    enum class ButtonSize {
        Medium, Small
    }

    enum class ButtonVariant {
        Primary, Secondary, Outline, Negative
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

    var size = ButtonSize.Medium
        set(value) {
            field = value

            val dp12 = resources.getDimensionPixelSize(R.dimen.dimen_12dp)
            val left = if (value == ButtonSize.Small) resources.getDimensionPixelSize(R.dimen.dimen_12dp)
                else resources.getDimensionPixelSize(R.dimen.dimen_16dp)

            setPadding(left, dp12, left, dp12)

            TextViewCompat.setTextAppearance(this, if (value == ButtonSize.Small) R.style.H4
                else R.style.H2)
        }

    var variant = ButtonVariant.Primary
        set(value) {
            field = value

            isSelected = variant == ButtonVariant.Secondary || variant == ButtonVariant.Negative
            isActivated = variant == ButtonVariant.Outline || variant == ButtonVariant.Negative

        }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonView,
                0, 0
            )

            val v = a.getInt(R.styleable.ButtonView_variant, 0)
            variant = ButtonVariant.values()[v]

            val s = a.getInt(R.styleable.ButtonView_size, 0)
            size = ButtonSize.values()[s]

            a.recycle()
        }
        else {
            size = ButtonSize.Medium
            variant = ButtonVariant.Primary
        }

        setBackgroundResource(R.drawable.bg_button)
        setTextColor(ContextCompat.getColorStateList(context, R.color.color_button))

        gravity = Gravity.CENTER_HORIZONTAL
    }
}