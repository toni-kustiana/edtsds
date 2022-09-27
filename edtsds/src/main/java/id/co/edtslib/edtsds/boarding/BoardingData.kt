package id.co.edtslib.edtsds.boarding

import com.google.gson.annotations.SerializedName

data class BoardingData(
    @SerializedName("image")
    val image: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)
