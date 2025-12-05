package id.co.edtslib.edtsds.bottom

interface BottomLayoutDelegate {
    /**
     * Triggered on click outside tray or back press
     */
    fun onDismiss()

    /**
     * Triggered on swipe down until bottom
     */
    fun onCollapse()

    /**
     * Triggered on fully expanded
     */
    fun onExpand()

    /**
     * Triggered on click 'X' (close) Button
     */
    fun onClose(){}

    /**
     * Override with true if you want to prevent closing tray (by swipe, back, and X button),
     * it will cancel the closing process, useful if you want to add confirmation dialog
     * in case there is progress happening on tray.
     *
     * Override with false if you doesn't need to prevent user from closing tray.
     *
     * Sample
     * ```
     * override fun onInterceptDismiss(): Boolean {
     *      if (interceptEnabled){
     *          showConfirmationDialog()
     *          return true
     *      }
     *      return false
     *  }
     *
     *private fun showConfirmationDialog() {
     *   val bottomLayout = findViewById<BottomLayout>(R.id.bottomLayout)
     *   AlertDialog.Builder(this)
     *       .setTitle("Closing Intercepted")
     *       .setMessage("This closing action is intercepted")
     *       .setCancelable(false) // Prevent clicking outside this alert
     *       .setPositiveButton("Continue") { _, _ ->
     *           bottomLayout.collapse()
     *       }
     *       .setNegativeButton("Cancel") { alert, _ ->
     *           bottomLayout.showFull()
     *           alert.dismiss()
     *       }
     *       .show()
     * }
     * ```
     */
    fun onInterceptDismiss(): Boolean = false
}