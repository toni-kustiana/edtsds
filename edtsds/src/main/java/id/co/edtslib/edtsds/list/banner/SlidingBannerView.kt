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

    override var data: List<String>
        get() = super.data
        set(value) {
            if (bannerScale > 0f) {
                post {
                    super.data = value
                }
            }
            else {
                super.data = value
            }
        }
    var bannerScale = 0f
        set(value) {
            field = value
            if (adapter is BannerAdapter) {
                (adapter as BannerAdapter).scale = value
            }
            post {
                if (bannerScale > 0f) {
                    layoutParams.height = (bannerScale*width).toInt() + paddingTop + paddingBottom
                }
                else {
                    layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }

        }


    override fun init(attrs: AttributeSet?) {
        super.init(attrs)

        snap = true

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingBannerView,
                0, 0
            )

            bannerScale = a.getFloat(
                R.styleable.SlidingBannerView_bannerScale, 0f)

            val roundedCorners = a.getDimensionPixelSize(
                R.styleable.SlidingBannerView_bannerCorner, 0)

            val adapter = adapter as BannerAdapter
            adapter.roundedCorners = roundedCorners

            a.recycle()
        }
        else {
            bannerScale = 0f
        }
    }
}