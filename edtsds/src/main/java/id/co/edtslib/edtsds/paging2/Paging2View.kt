package id.co.edtslib.edtsds.paging2

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.co.edtslib.baserecyclerview2.AdapterData
import id.co.edtslib.baserecyclerview2.BaseRecyclerView2
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Paging2View<T, L>: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var loading = false
    private var page = 0
    var size = 10
    var totalPages = Int.MAX_VALUE
    var delegate: Paging2Delegate<T>? = null
    var itemOrdinal = 0
    var itemIndex = 0

    fun loadPage(page: Int) {
        loading = true

        if (context is FragmentActivity) {
            (context as FragmentActivity).lifecycleScope.launch {
                delegate?.loadPage(page, size)?.collectLatest {
                    delegate?.processResult(it)
                    loading = false
                    if (page > 0) {
                        delegate?.onNextPageLoaded()
                    }
                }
            }
        }
    }

    fun setData(list: List<L>?, page: Int, totalPage: Int) {
        totalPages = totalPage
        this.page = page

        val baseAdapter = adapter as BaseRecyclerView2

        var offset = page*size + itemIndex

        val mList = list ?: listOf()
        val listSize = baseAdapter.list.size

        mList.forEach {
            if (offset < listSize) {
                baseAdapter.list[offset] = AdapterData(itemOrdinal, it)
                baseAdapter.notifyItemChanged(offset)
            }
            else {
                baseAdapter.list.add(AdapterData(itemOrdinal, it))
                baseAdapter.notifyItemInserted(baseAdapter.list.size-1)
            }

            offset++
        }

        while (offset < baseAdapter.list.size) {
            baseAdapter.list.removeAt(offset)
            baseAdapter.notifyItemRemoved(offset)
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        val baseAdapter = adapter as BaseRecyclerView2
        val last = when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                var index = -1
                for (i in 0 until staggeredGridLayoutManager.spanCount) {
                    val temp = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null)[i]
                    if (index < temp) {
                        index = temp
                    }
                }
                index
            }
            is LinearLayoutManager -> {
                val linearLayoutManager = layoutManager as LinearLayoutManager
                linearLayoutManager.findLastCompletelyVisibleItemPosition()
            }
            else -> {
                -1
            }
        }


        if (last+1 >= baseAdapter.list.size) {
            if (! loading && page+1 < totalPages) {
                loading = true
                delegate?.onNextPageLoading()
                postDelayed({
                    loadPage(page+1)
                }, 500)
            }
        }

        super.onScrolled(dx, dy)
    }
}