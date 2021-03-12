package makeus6.hackathon.homecafe.src.main.home.models

import com.google.gson.annotations.SerializedName



data class HomeResponse (
        @SerializedName("code") val code:String,
        @SerializedName("message")val message:String,
        @SerializedName("data") val data:MutableList<data>

)

data class data(
        @SerializedName("board") val board:MutableList<boardData>,
        @SerializedName("creator")val creator:MutableList<creator>
)

data class boardData(
        val id:Int,
        val description:String,
        val likesCount:Int,
        val commentsCount:Int,
        @SerializedName("pictureUrls") val url:MutableList<String>
)

data class creator(
        val id:Int,
        val email:String,
        val name:String,
        val profileUrl:String
)


