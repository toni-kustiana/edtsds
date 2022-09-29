package id.co.edtslib.edtsds.chips

interface SlidingChipsDelegate<T> {
    fun onSelected(item: T, position: Int)
}