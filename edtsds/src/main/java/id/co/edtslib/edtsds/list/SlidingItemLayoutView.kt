package id.co.edtslib.edtsds.list

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.bumptech.glide.Glide
import id.co.edtslib.edtsds.R

class SlidingItemLayoutView: FrameLayout {
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

    private var imageView: AppCompatImageView? = null
    var imageViewResId = 0
    var imageUrl: String? = null
    private var drawableWidth = 0f

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SlidingItemLayoutView,
                0, 0
            )

            drawableWidth = a.getDimension(R.styleable.SlidingItemLayoutView_drawableWidth, 0f)
            imageViewResId = a.getResourceId(R.styleable.SlidingItemLayoutView_drawableStartCompat, 0)

            a.recycle()
        }
    }

    fun redraw() {
        if (imageViewResId != 0 || imageUrl != null) {
            if (imageView == null) {
                postDelayed( {
                    if (imageView == null) {
                        val h = height

                        imageView = AppCompatImageView(context)
                        addView(imageView, 0)

                        imageView?.scaleType = ImageView.ScaleType.FIT_XY

                        if (imageUrl != null) {
                            try {
                                Glide.with(imageView!!.context).load(imageUrl).into(imageView!!)
                            } catch (ignore: IllegalArgumentException) {
                                imageView?.setImageResource(imageViewResId)
                            }
                        } else {
                            imageView?.setImageResource(imageViewResId)
                        }

                        val frameLayout = imageView?.layoutParams
                        frameLayout?.width = drawableWidth.toInt()
                        frameLayout?.height = h - paddingTop - paddingBottom

                        if (childCount > 1) {
                            val view = getChildAt(1)
                            if (view is SlidingItemView<*, *>) {
                                setScrollListener(view)
                            }
                        }
                    }
                    else {
                        onBackgroundReady()
                    }

                }, 500)
            }
            else {
                onBackgroundReady()
            }
        }
    }

    private fun onBackgroundReady() {
        if (imageUrl != null) {
            try {
                Glide.with(imageView!!.context).load(imageUrl).into(imageView!!)
            }
            catch (ignore: IllegalArgumentException) {
                imageView?.setImageResource(imageViewResId)
            }
        }
        else {
            imageView?.setImageResource(imageViewResId)
        }
    }

    private fun setScrollListener(slidingItemView: SlidingItemView<*, *>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollImage(slidingItemView)

            slidingItemView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    scrollImage(recyclerView)

                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }
    }

    private fun scrollImage(recyclerView: RecyclerView) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val first = layoutManager.findFirstVisibleItemPosition()
            if (first == 0) {
                val view = layoutManager.findViewByPosition(first)
                val widthAlphaMax = recyclerView.paddingStart.toFloat()*2.0f/3f

                val left = if (view == null) 0 else if (view.left < 0) 0 else view.left
                val remain = recyclerView.paddingStart.toFloat() - left
                imageView?.alpha = left*1f/widthAlphaMax

                val pct = remain/5.0f
                imageView?.translationX = -pct
            }
            else {
                if (imageView!!.translationX != -imageView !!.width.toFloat()) {
                    imageView!!.translationX = -imageView!!.width.toFloat()
                }
            }
        }
    }

}