package id.co.edtslib.edtsds.boarding

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
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

    enum class Alignment {
        Left, Center, Right
    }

    private val binding: ViewBoardingBinding =
        ViewBoardingBinding.inflate(LayoutInflater.from(context), this, true)

    var autoScrollInterval = 0
    private var runnable: Runnable? = null

    var canBackOnFirstPosition = false
    var circular = false
        set(value) {
            field = value
            adapter.circular = value
        }

    val pagingNavigationView = binding.navigation
    val viewPager = binding.viewPager
    var alignment = Alignment.Left
        set(value) {
            field = value
            val layoutParams = binding.navigation.layoutParams as LinearLayoutCompat.LayoutParams
            when (value) {
                Alignment.Center -> {
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL
                }
                Alignment.Right -> {
                    layoutParams.gravity = Gravity.END
                }
                else -> {
                    layoutParams.gravity = Gravity.START
                }
            }
            adapter.alignment = value
        }

    var list: List<BoardingData>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            if (value?.isNotEmpty() == true) {
                binding.navigation.count = value.size

                adapter.list = value.toMutableList()
                adapter.notifyDataSetChanged()

                binding.navigation.selectedIndex = 0
                if (autoScrollInterval > 0) {
                    startAutoScroll()
                }

                viewPager.post {
                    viewPager.setCurrentItem(adapter.getInitialPosition(canBackOnFirstPosition), false)
                }
            }
            else {
                removeAutoScroll()
                isVisible = false
            }
        }

    private fun removeAutoScroll() {
        if (runnable != null) {
            binding.viewPager.removeCallbacks(runnable)
        }
        runnable = null
    }

    private fun startAutoScroll() {
        removeAutoScroll()
        if (list?.isNotEmpty() == true) {
            runnable = Runnable {
                val currentItem = binding.viewPager.currentItem
                if (currentItem == list!!.size - 1) {
                    binding.viewPager.currentItem = adapter.getInitialPosition(canBackOnFirstPosition)
                } else {
                    binding.viewPager.currentItem = currentItem + 1
                }

                startAutoScroll()
            }
            binding.viewPager.postDelayed(runnable, autoScrollInterval*1000L)
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

            autoScrollInterval = a.getInt(R.styleable.BoardingView_autoScrollInterval, 0)
            circular = a.getBoolean(R.styleable.BoardingView_circular, false)
            canBackOnFirstPosition = a.getBoolean(R.styleable.BoardingView_canBackOnFirstPosition, false)

            val iAlignment = a.getInt(R.styleable.BoardingView_alignment, 0)
            alignment = Alignment.values()[iAlignment]


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
                val fakePosition = adapter.getFakePosition(position)
                if (binding.viewPager.currentItem != fakePosition) {
                    binding.viewPager.currentItem = fakePosition
                }
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                delegate?.onSelected(position)
                if (binding.navigation.selectedIndex != position) {
                    binding.navigation.selectedIndex = adapter.getRealPosition(position)
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (autoScrollInterval > 0) {
                    startAutoScroll()
                }
            }
        })
    }
}