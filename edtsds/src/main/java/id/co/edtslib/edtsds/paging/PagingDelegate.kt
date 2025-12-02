package id.co.edtslib.edtsds.paging

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface PagingDelegate<T> {
    fun loadPage(page: Int, size: Int): Flow<T>
    fun processResult(t: T)
    fun onNextPageLoading()
    fun onNextPageLoaded()
}