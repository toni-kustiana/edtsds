package id.co.edtslib.edtsds.paging2

import androidx.lifecycle.LiveData

interface Paging2Delegate<T> {
    fun loadPage(page: Int, size: Int): LiveData<T>
    fun processResult(t: T)
    fun onNextPageLoading()
    fun onNextPageLoaded()
}