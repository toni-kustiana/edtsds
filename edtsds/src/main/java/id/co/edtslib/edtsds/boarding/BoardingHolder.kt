package id.co.edtslib.edtsds.boarding

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginStart
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.Util
import id.co.edtslib.edtsds.databinding.AdapterBoardingItemBinding

class BoardingHolder(private val viewBinding: AdapterBoardingItemBinding,
                     private var alignment: BoardingView.Alignment,
                     private val imageMargin: Float,
                     titleStyle: Int = 0,
                     descriptionStyle: Int = 0
) : BaseViewHolder<BoardingData>(viewBinding) {
    init {
        if (titleStyle != 0) {
            TextViewCompat.setTextAppearance(viewBinding.tvTitle, titleStyle)
        }
        if (descriptionStyle != 0) {
            TextViewCompat.setTextAppearance(viewBinding.tvDescription, descriptionStyle)
        }
    }

    init {
        val lp = viewBinding.imageView.layoutParams as LinearLayout.LayoutParams
        lp.marginStart = imageMargin.toInt()
        lp.marginEnd = imageMargin.toInt()

    }

    @SuppressLint("DiscouragedApi")
    override fun setData(
        list: MutableList<BoardingData>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<BoardingData>?
    ) {
        viewBinding.tvTitle.text = list[position].title
        val layoutParamsTitle = viewBinding.tvTitle.layoutParams as LinearLayoutCompat.LayoutParams

        viewBinding.tvDescription.text = list[position].description
        val layoutParamsDescription = viewBinding.tvDescription.layoutParams as LinearLayoutCompat.LayoutParams

        when (alignment) {
            BoardingView.Alignment.Center -> {
                viewBinding.tvTitle.gravity = Gravity.CENTER_HORIZONTAL
                layoutParamsTitle.gravity = Gravity.CENTER_HORIZONTAL

                viewBinding.tvDescription.gravity = Gravity.CENTER_HORIZONTAL
                layoutParamsDescription.gravity = Gravity.CENTER_HORIZONTAL
            }
            BoardingView.Alignment.Right -> {
                viewBinding.tvTitle.gravity = Gravity.END
                layoutParamsTitle.gravity = Gravity.END

                viewBinding.tvDescription.gravity = Gravity.END
                layoutParamsDescription.gravity = Gravity.END
            }
            else -> {
                viewBinding.tvTitle.gravity = Gravity.START
                layoutParamsTitle.gravity = Gravity.START

                viewBinding.tvDescription.gravity = Gravity.START
                layoutParamsDescription.gravity = Gravity.START
            }
        }

        val image = list[position].image
        if (image != null) {
            if (image.startsWith("http")) {
                Glide.with(viewBinding.root).load(image).into(viewBinding.imageView)
            }
            else {
                if (viewBinding.root.context is FragmentActivity) {
                    val fragmentActivity = viewBinding.root.context as FragmentActivity
                    val resourceId = fragmentActivity.resources.getIdentifier(
                        image, "drawable",
                        fragmentActivity.packageName
                    )
                    if (Util.isValidContext(viewBinding.root.context)) {
                        Glide.with(fragmentActivity).load(resourceId).into(viewBinding.imageView)
                    }
                }
            }
        }
    }

}