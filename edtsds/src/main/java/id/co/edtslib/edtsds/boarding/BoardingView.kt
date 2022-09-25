package id.co.edtslib.edtsds.boarding

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import id.co.edtslib.edtsds.R
import id.co.edtslib.edtsds.databinding.ViewBoardingBinding
import id.co.edtslib.edtsds.pagingnavigation.PagingNavigationDelegate

class BoardingView: FrameLayout {
    private val adapter = BoardingAdapter()
    var delegate: BoardingDelegate? = null

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

    private val binding: ViewBoardingBinding =
        ViewBoardingBinding.inflate(LayoutInflater.from(context), this, true)

    val pagingNavigationView = binding.navigation
    val viewPager = binding.viewPager

    var list: List<BoardingData>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            if (value?.isNotEmpty() == true) {
                binding.navigation.count = value.size

                adapter.list = value.toMutableList()
                adapter.notifyDataSetChanged()

                binding.navigation.selectedIndex = 0
            }
            else {
                isVisible = false
            }
        }

    private fun init(attrs: AttributeSet?) {
        setup()

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BoardingView,
                0, 0
            )

            binding.navigation.space = a.getDimension(
                R.styleable.BoardingView_space,
                resources.getDimensionPixelSize(R.dimen.dimen_8dp).toFloat())
            binding.navigation.shapeSize = a.getDimension(
                R.styleable.BoardingView_shapeSize,
                resources.getDimensionPixelSize(R.dimen.dimen_4dp).toFloat())
            binding.navigation.shapeSelectedWidth = a.getDimension(
                R.styleable.BoardingView_shapeSelectedWidth,
                binding.navigation.shapeSize)
            binding.navigation.shapeSelectedHeight = a.getDimension(
                R.styleable.BoardingView_shapeSelectedHeight,
                binding.navigation.shapeSize)

            binding.navigation.shapeResId = a.getResourceId(
                R.styleable.BoardingView_shape,
                R.drawable.bg_navigation)


            val fullHeight = a.getBoolean(R.styleable.BoardingView_pagerFullHeight, false)
            if (fullHeight) {
                val lpRoot = binding.linearLayout.layoutParams
                lpRoot.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT

                val lpViewPager = binding.viewPager.layoutParams as LinearLayoutCompat.LayoutParams
                lpViewPager.height = 0
                lpViewPager.weight = 1f

            }

            adapter.height = a.getDimension(
                R.styleable.BoardingView_imageHeight,
                context.resources.getDimensionPixelSize(R.dimen.boarding_image_height).toFloat())

            a.recycle()
        }
    }

    private fun setup() {
        binding.viewPager.adapter = adapter
        binding.navigation.delegate = object : PagingNavigationDelegate {
            override fun onSelected(position: Int) {

                if (binding.viewPager.currentItem != position) {
                    binding.viewPager.currentItem = position
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                delegate?.onSelected(position)
                if (binding.navigation.selectedIndex != position) {
                    binding.navigation.selectedIndex = position
                }
            }
        })
    }
}