package id.co.edtslib.edtsds.chips.group

interface GroupChipDelegate<T> {
    fun onSelected(item: T, position: Int)

}