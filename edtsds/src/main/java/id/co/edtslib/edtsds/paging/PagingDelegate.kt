package id.co.edtslib.edtsds.paging

import androidx.lifecycle.LiveData

interface PagingDelegate<T> {
    fun loadPage(page: Int): LiveData<T>
    fun processResult(t: T)
    fun onNextPageLoading()
    fun onNextPageLoaded()
}