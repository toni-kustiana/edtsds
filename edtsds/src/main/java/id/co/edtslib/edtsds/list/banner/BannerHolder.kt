package id.co.edtslib.edtsds.list.banner

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.edtsds.databinding.AdapterBannerBinding

class BannerHolder(private val binding: AdapterBannerBinding, private var height: Int,
                   private var roundedCorners: Int):
    BaseViewHolder<String>(binding) {
    override fun setData(
        list: MutableList<String>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<String>?
    ) {
        if (height != 0) {
            binding.imageView.scaleType = ImageView.ScaleType.FIT_XY
            if (binding.imageView.layoutParams is RecyclerView.LayoutParams) {
                val lp = binding.imageView.layoutParams as RecyclerView.LayoutParams
                if (lp.height != height) {
                    lp.height = height
                    binding.imageView.post {
                        loadImage(list[position])
                    }
                }
                else {
                    loadImage(list[position])
                }
            }
        }
        else {
            loadImage(list[position])
        }
    }

    private fun loadImage(url: String) {
        if (roundedCorners > 0) {
            Glide.with(binding.root.context).load(url).transform(RoundedCorners(roundedCorners)).into(binding.imageView)
        }
        else {
            Glide.with(binding.root.context).load(url).into(binding.imageView)

        }
    }
}