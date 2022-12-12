package id.co.edtslib.edtsds.badge

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import id.co.edtslib.edtsds.R

class BadgeView: MaterialTextView {

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
            }

            a.recycle()
        }
        else {
            badgeType = BadgeType.Primary
        }

        if (badgeType != BadgeType.Custom) {
            setBackgroundResource(R.drawable.bg_badge)
            setTextColor(ContextCompat.getColorStateList(context, R.color.color_badge))
        }

        gravity = Gravity.CENTER_HORIZONTAL

        if (badgeType != BadgeType.Custom) {
            val size2InDp = 2
            val size4InDp = 4
            val scale = resources.displayMetrics.density
            val dp2AsPixels = (size2InDp * scale + 0.5f).toInt()
            val dp4AsPixels = (size4InDp * scale + 0.5f).toInt()
            setPadding(dp4AsPixels, dp2AsPixels, dp4AsPixels, dp2AsPixels)
        }

    }
}