package id.co.edtslib.edtsds.boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterBoardingItemBinding

class BoardingAdapter: BaseRecyclerViewAdapter<AdapterBoardingItemBinding, BoardingData>() {
    var imageMargin = 0f
    var alignment = BoardingView.Alignment.Left
    var circular = false
    var titleStyle: Int = 0
    var descriptionStyle: Int = 0

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterBoardingItemBinding
        get() = AdapterBoardingItemBinding::inflate

    override fun createHolder(): BoardingHolder =
        BoardingHolder(
            viewBinding = binding,
            alignment = alignment,
            titleStyle = titleStyle,
            imageMargin = imageMargin,
            descriptionStyle = descriptionStyle)

    override fun getItemCount() = if (circular && list.size > 1) Int.MAX_VALUE else super.getItemCount()

    override fun onBindViewHolder(holder: BaseViewHolder<BoardingData>, position: Int) {
        if (circular && list.size > 1) {
            val newPosition = position % list.size

            if (clickable()) {
                holder.itemView.setOnClickListener {
                    delegate?.onClick(list[newPosition], newPosition, holder)
                }
            }
            holder.setData(list, newPosition, delegate)
            delegate?.onDraw(list[newPosition], newPosition)
        }
        else {
            super.onBindViewHolder(holder, position)
        }
    }

    fun getRealPosition(position: Int): Int {
        return if (circular && list.size > 1) {
            position % list.size
        } else {
            position
        }
    }

    fun getFakePosition(position: Int): Int {
        return if (circular && list.size > 1) {
            (itemCount / 2 - (itemCount/2) % list.size)+position
        } else {
            position
        }
    }

    fun getInitialPosition(canBackOnFirstPosition: Boolean): Int {
        return if (circular && list.size > 1) {
            if (canBackOnFirstPosition) {
                itemCount / 2 - (itemCount/2) % list.size
            } else {
                0
            }
        } else {
            0
        }
    }
}