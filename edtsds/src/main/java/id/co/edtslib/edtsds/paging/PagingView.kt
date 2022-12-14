package id.co.edtslib.edtsds.paging

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter

class PagingView<T, L>: RecyclerView {
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
    var delegate: PagingDelegate<T>? = null

    fun loadPage(page: Int) {
        loading = true
        delegate?.loadPage(page, size)?.observe(context as LifecycleOwner) {
            delegate?.processResult(it)
            loading = false
            if (page > 0) {
                delegate?.onNextPageLoaded()
            }
            this.page++
        }
    }

    fun setData(list: List<L>?, page: Int, totalPage: Int) {
        totalPages = totalPage
        val baseAdapter = adapter as BaseRecyclerViewAdapter<*, L>

        val offset = page*size
        val mList = list?.toMutableList()
        for (i in 0 until  size) {
            val index = i + offset
            if (mList?.isNotEmpty() != true) {
                // jika ada di adapter, remove it
                if (index+1 < baseAdapter.list.size) {
                    baseAdapter.list.removeAt(index)
                    baseAdapter.notifyItemRemoved(index)
                }
            }
            else {
                val l = mList.removeFirst()
                if (index+1 < baseAdapter.list.size) {
                    baseAdapter.list[index] = l
                    baseAdapter.notifyItemChanged(index)
                }
                else {
                    baseAdapter.list.add(l)
                    baseAdapter.notifyItemInserted(baseAdapter.list.size-1)
                }
            }
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        val baseAdapter = adapter as BaseRecyclerViewAdapter<*, L>
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
            if (! loading && page < totalPages) {
                loading = true
                delegate?.onNextPageLoading()
                postDelayed({
                    loadPage(page)
                }, 500)
            }
        }

        super.onScrolled(dx, dy)
    }
}