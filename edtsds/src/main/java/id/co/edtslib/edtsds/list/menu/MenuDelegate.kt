package id.co.edtslib.edtsds.list.menu

interface MenuDelegate<T> {
    fun onSelected(t: T)
}