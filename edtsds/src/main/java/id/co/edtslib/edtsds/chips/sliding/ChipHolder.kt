package id.co.edtslib.edtsds.chips.sliding

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.AdapterChipBinding

class ChipHolder<T>(private val binding: AdapterChipBinding,
                    textColor: Int,
                    textPadding: Float,
                    chipBackgroundColor: Int,
                    strokeColor: Int,
                    textStyle: Int,
                    private val iconPaddingStart: Float,
                    private val iconPaddingEnd: Float,
                    private val iconSize: Float):
    BaseViewHolder<T>(binding) {
    init {
        binding.chip.setTextColor(
            ContextCompat.getColorStateList(
                itemView.context,
                textColor
            )
        )

        binding.chip.chipStartPadding = textPadding
        binding.chip.chipEndPadding = textPadding

        binding.chip.chipBackgroundColor = ContextCompat.getColorStateList(
            itemView.context,
            chipBackgroundColor
        )

        if (strokeColor != 0) {
            binding.chip.chipStrokeColor = ContextCompat.getColorStateList(
                itemView.context,
                strokeColor
            )
            binding.chip.chipStrokeWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.dimen_1dp).toFloat()
        }

        if (textStyle > 0) {
            TextViewCompat.setTextAppearance(binding.chip, textStyle)
        }

    }
    override fun setData(
        list: MutableList<T>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<T>?
    ) {
        val item = list[position]
        if (item is RecyclerData<*>) {
            if (item.data is ChipItemData) {
                binding.chip.text = item.data.text
                binding.chip.isChipIconVisible = item.data.icon != null ||
                        item.data.url?.isNotEmpty() == true
                if (binding.chip.isChipIconVisible) {
                    binding.chip.chipIconSize = iconSize
                    binding.chip.chipStartPadding = iconPaddingStart
                    binding.chip.chipEndPadding = iconPaddingEnd

                    if (item.data.icon != null) {
                        binding.chip.chipIcon =
                            ContextCompat.getDrawable(itemView.context, item.data.icon!!)
                    }
                    else {
                        binding.chip.chipIcon =
                            ContextCompat.getDrawable(itemView.context, R.drawable.px1)
                        Glide.with(itemView.context).load(item.data.url).
                            listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding.chip.isChipIconVisible = true
                                    return true
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding.chip.isChipIconVisible = resource != null
                                    if (resource != null) {
                                        binding.chip.chipIcon = resource
                                    }
                                    return true
                                }

                            }).submit()
                    }
                }
                else {
                    binding.chip.chipStartPadding = 0f
                    binding.chip.chipEndPadding = 0f
                }
            }
            else {
                binding.chip.text = item.data.toString()
            }
            itemView.isSelected = item.selected

            binding.chip.setOnClickListener {
                delegate?.onClick(item, position, this)
            }

        }
    }
}