package id.co.edtslib.edtsds.chips.sliding

interface SlidingChipsDelegate<T> {
    fun onSelected(item: T, position: Int)
}