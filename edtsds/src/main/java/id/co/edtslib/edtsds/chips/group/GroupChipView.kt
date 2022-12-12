package id.co.edtslib.edtsds.chips.group

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import id.co.edtslib.edtsds.R

class GroupChipView<T> : ChipGroup {
    var delegate: GroupChipDelegate<T>? = null

    private var textColor = R.color.color_text_chip
    private var strokeColor = 0
    private var chipBackgroundColor = R.color.color_bg_chip
    private var textPadding = 0f
    private var textStyle = 0

    var items: MutableList<T> = mutableListOf()
        set(value) {
            field = value

            removeAllViews()
            for ((i, item) in items.withIndex()) {

                val chip = Chip(context)
                chip.text = item.toString()

                if (textStyle > 0) {
                    TextViewCompat.setTextAppearance(chip, textStyle)
                }


                chip.tag = item
                chip.setTextColor(
                    ContextCompat.getColorStateList(
                        context,
                        textColor
                    )
                )
                chip.chipStartPadding = textPadding
                chip.chipEndPadding = textPadding

                chip.chipBackgroundColor = ContextCompat.getColorStateList(
                    context,
                    chipBackgroundColor
                )
                if (strokeColor != 0) {
                    chip.chipStrokeColor = ContextCompat.getColorStateList(
                        context,
                        strokeColor
                    )
                    chip.chipStrokeWidth = context.resources.getDimensionPixelSize(R.dimen.chip_dimen_1dp).toFloat()
                }
                chip.setOnClickListener {
                    repeat (childCount) {idx ->
                        getChildAt(idx).isSelected = false
                    }
                    chip.isSelected = true

                    val left = it.left - scrollX
                    val space = resources.getDimensionPixelSize(R.dimen.chip_dimen_16dp)

                    // if partial show on right
                    if (it.width + left + space > width) {
                        val diff = width - it.width - left - space
                        val scrollX = scrollX - diff
                        scrollTo(scrollX, 0)
                    }

                    delegate?.onSelected(item, i)
                }

                chip.isSelected = selectionIndex == i

                addView(chip)
            }
        }

    var selectionIndex = 0
        set(value) {
            field = value

            repeat(childCount) {
                getChildAt(it).isSelected = it == value
            }
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

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.GroupChipView,
                0, 0
            )

            val startEndSpace = a.getDimension(R.styleable.GroupChipView_groupChipMargin,
                resources.getDimensionPixelSize(R.dimen.chip_dimen_16dp).toFloat())

            textColor = a.getResourceId(R.styleable.GroupChipView_groupChipTextColor,
                R.color.color_text_chip)

            strokeColor = a.getResourceId(R.styleable.GroupChipView_groupChipStrokeColor,
                0)

            textPadding = a.getDimension(R.styleable.GroupChipView_groupChipTextPadding,
                resources.getDimensionPixelSize(R.dimen.chip_dimen_20dp).toFloat())

            chipBackgroundColor = a.getResourceId(R.styleable.GroupChipView_groupChipBackground,
                R.color.color_bg_chip)

            textStyle = a.getResourceId(R.styleable.GroupChipView_groupChipTextStyle, 0)

            isSingleSelection = true


            setPadding(startEndSpace.toInt(), 0, startEndSpace.toInt(), 0)

            a.recycle()
        }
    }
}