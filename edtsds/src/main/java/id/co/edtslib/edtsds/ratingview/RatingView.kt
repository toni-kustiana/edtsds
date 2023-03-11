package id.co.edtslib.edtsds.ratingview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import id.co.edtslib.edtsds.R

class RatingView : LinearLayoutCompat {
    constructor(context: Context) : super(context)
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

    var delegate: RatingDelegate? = null
    var count = 5
        set(value) {
            field = value

            removeAllViews()
            for (i in 0 until value) {
                val imageView = AppCompatImageView(context)
                imageView.setImageResource(R.drawable.ds_ic_star)
                imageView.setOnClickListener {
                    setRating(i)
                }


                addView(imageView)

                if (i > 0) {
                    val dp8 = context.resources.getDimensionPixelSize(R.dimen.dimen_8dp)

                    val layoutParams = imageView.layoutParams as LayoutParams
                    layoutParams.marginStart = dp8
                }
            }
        }

    val animation = true
    val rating: Int
        get(): Int {
            var v = 0
            var found = true
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                if (view.isSelected) {
                    v++
                    found = true
                }
            }

            return if (found) v + 1 else 0
        }

    var value: Int = 0
        set(value) {
            field = value
            val rating = if (value < 0) {
                0
            } else if (value > count) {
                count
            } else value
            setRating(rating - 1)
        }

    private fun init(attrs: AttributeSet?) {
        orientation = HORIZONTAL

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RatingView,
                0, 0
            )

            count = a.getInt(R.styleable.RatingView_count, 5)

            a.recycle()
        } else {
            count = 5
        }
    }

    private fun setRating(index: Int) {
        for (i in 0 until childCount) {
            val isSelected = i <= index
            get(i).isSelected = isSelected
            if (isSelected && animation) {
                get(i).scaleX = 1.2f
                get(i).scaleY = 1.2f

                get(i).animate().scaleX(1f).scaleY(1f)
            }
        }
        delegate?.onChanged(index + 1)
    }
}