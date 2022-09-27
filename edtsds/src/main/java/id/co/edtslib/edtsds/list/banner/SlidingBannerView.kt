package id.co.edtslib.edtsds.list.banner

import android.content.Context
import android.util.AttributeSet
import id.co.edtslib.edtsds.R
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

    override fun init(attrs: AttributeSet?) {
        super.init(attrs)

        snap = true

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingBannerView,
                0, 0
            )

            val bannerHeight = a.getDimensionPixelSize(
                R.styleable.SlidingBannerView_bannerHeight, 0)

            val roundedCorners = a.getDimensionPixelSize(
                R.styleable.SlidingBannerView_bannerCorner, 0)

            val adapter = adapter as BannerAdapter
            adapter.height = bannerHeight
            adapter.roundedCorners = roundedCorners

            a.recycle()
        }
    }
}