package id.co.edtslib.edtsds.myapplication

import id.co.edtslib.edtsds.list.checkmenu.DataSelected

class Test(private val p: String): DataSelected() {
    override fun toString() = p
}