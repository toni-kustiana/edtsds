package id.co.edtslib.edtsds.bottom

interface BottomLayoutDelegate {
    fun onDismiss()
    fun onCollapse()
    fun onExpand()
    fun onClose(){}
    fun onInterceptDismiss(): Boolean = false
}