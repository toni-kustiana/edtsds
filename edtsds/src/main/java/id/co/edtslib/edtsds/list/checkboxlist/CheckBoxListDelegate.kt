package id.co.edtslib.edtsds.list.checkboxlist

interface CheckBoxListDelegate<T> {
    fun onSelected(t: T)
    fun onSelected(t: List<T>)
}