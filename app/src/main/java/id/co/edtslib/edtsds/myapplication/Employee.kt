package id.co.edtslib.edtsds.myapplication

import id.co.edtslib.edtsds.list.checkboxlist.DataSelected


class Employee(private val name: String): DataSelected() {
    override fun toString() = name
}