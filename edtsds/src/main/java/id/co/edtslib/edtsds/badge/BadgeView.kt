package id.co.edtslib.edtsds.badge

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.textview.MaterialTextView
import id.co.edtslib.edtsds.R

class BadgeView : MaterialTextView {

    enum class BadgeType {
        Primary, Secondary, Neutral, Important, Custom
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

    var badgeType = BadgeType.Primary
        set(value) {
            field = value

            isSelected = badgeType == BadgeType.Secondary || badgeType == BadgeType.Important
            isActivated = badgeType == BadgeType.Neutral || badgeType == BadgeType.Important

        }

    var badgeLabel = ""
        set(value) {
            field = value

        }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BadgeView,
                0, 0
            )

            val v = a.getInt(R.styleable.BadgeView_badgeType, 0)
            badgeType = BadgeType.values()[v]

            if (badgeType != BadgeType.Custom) {
                val t = a.getString(R.styleable.BadgeView_label)
                text = t ?: ""
            } else {
                if (badgeLabel.isNotEmpty()) {
                    text = badgeLabel
                }
            }

            a.recycle()
        } else {
            badgeType = BadgeType.Primary
        }

        if (badgeType != BadgeType.Custom) {
            setBackgroundResource(R.drawable.edts_bg_badge)
            setTextColor(ContextCompat.getColorStateList(context, R.color.edts_color_badge))
        }

        TextViewCompat.setTextAppearance(this, R.style.H4)

        gravity = Gravity.CENTER

        val left = resources.getDimensionPixelSize(R.dimen.dimen_4dp)
        val top = resources.getDimensionPixelSize(R.dimen.dimen_2dp)

        setPadding(left, top, left, top)

    }
}