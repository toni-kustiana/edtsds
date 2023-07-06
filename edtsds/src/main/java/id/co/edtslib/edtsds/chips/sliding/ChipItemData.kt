package id.co.edtslib.edtsds.chips.sliding

class ChipItemData{
    var text: String = ""
    var icon: Int? = null
    var url: String?= null

    constructor(text: String) {
        this.text = text
    }

    constructor(text: String, icon: Int) {
        this.text = text
        this.icon = icon
    }

    constructor(text: String, url: String?) {
        this.text = text
        this.url = url
    }

    override fun toString() = text
}