package id.co.edtslib.edtsds.list.banner

import android.content.Context
import android.util.AttributeSet
import id.co.edtslib.edtsds.databinding.AdapterBannerBinding
import id.co.edtslib.edtsds.list.SlidingItemView

class SlidingBannerView: SlidingItemView<AdapterBannerBinding, String> {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun createAdapter() = BannerAdapter()
    override fun isEqual(a: String, b: String) = a == b
}