package id.co.edtslib.edtsds.list.banner

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.Util
import id.co.edtslib.edtsds.databinding.AdapterBannerBinding

class BannerHolder(private val binding: AdapterBannerBinding, private var scale: Float,
                   private var roundedCorners: Int):
    BaseViewHolder<String>(binding) {
    override fun setData(
        list: MutableList<String>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<String>?
    ) {
        if (scale == 0f) {
            binding.imageView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        loadImage(list[position])
    }

    private fun loadImage(url: String) {
        if (roundedCorners > 0) {
            Glide.with(binding.root.context).load(url).
                transform(CenterInside(), RoundedCorners(roundedCorners)).
                placeholder(Util.shimmerDrawable).
                error(R.drawable.ic_broken_banner).
                into(binding.imageView)
        }
        else {
            Glide.with(binding.root.context).load(url).into(binding.imageView)

        }
    }
}