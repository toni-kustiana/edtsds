package id.co.edtslib.edtsds.badge

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import id.co.edtslib.edtsds.R

class BadgeView: AppCompatTextView {

    enum class BadgeType {
        Primary, Secondary, Neutral, Important
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

            val t = a.getString(R.styleable.BadgeView_label)
            text = t ?: ""

            a.recycle()
        }
        else {
            badgeType = BadgeType.Primary
        }

        setBackgroundResource(R.drawable.bg_badge)
        setTextColor(ContextCompat.getColorStateList(context, R.color.color_badge))

        gravity = Gravity.CENTER_HORIZONTAL

        val sizeInDp = 4
        val scale = resources.displayMetrics.density
        val dpAsPixels = (sizeInDp * scale + 0.5f).toInt()
        setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels)

    }
}