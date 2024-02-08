package id.co.edtslib.edtsds.paging2

import kotlinx.coroutines.flow.Flow

interface Paging2Delegate<T> {
    fun loadPage(page: Int, size: Int): Flow<T>
    fun processResult(t: T)
    fun onNextPageLoading()
    fun onNextPageLoaded()
}