package id.co.edtslib.edtsds.chips.sliding

class ChipItemData{
    var text: String = ""
    var icon: Int? = null
    var iconSelected: Int? = null
    var url: String?= null
    var urlSelected: String?= null

    constructor(text: String) {
        this.text = text
    }

    constructor(text: String, icon: Int, iconSelected: Int? = null) {
        this.text = text
        this.icon = icon
        this.iconSelected = iconSelected
    }

    constructor(text: String, url: String?, urlSelected: String? = null) {
        this.text = text
        this.url = url
        this.urlSelected = urlSelected
    }

    override fun toString() = text
}