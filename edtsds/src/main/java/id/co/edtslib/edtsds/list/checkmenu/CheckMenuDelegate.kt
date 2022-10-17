package id.co.edtslib.edtsds.list.checkmenu

interface CheckMenuDelegate<T> {
    fun onSelected(t: T)
}