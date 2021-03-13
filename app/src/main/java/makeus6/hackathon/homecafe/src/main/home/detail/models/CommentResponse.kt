package makeus6.hackathon.homecafe.src.main.home.detail.models

import com.google.gson.annotations.SerializedName
import makeus6.hackathon.homecafe.src.main.home.models.boardData
import makeus6.hackathon.homecafe.src.main.home.models.creator

data class CommentResponse (
    val code:String,
    val message:String,
    @SerializedName("data") val data:commentData
    )

data class commentData(
        @SerializedName("board") val board: boardData,
        @SerializedName("creator")val creator: creator,
        @SerializedName("comments") val comments:MutableList<comments>
)

data class boardData(
        val id:Int,
        val description:String,
        val likesCount:Int,
        val commentsCount:Int,
        @SerializedName("pictureUrls") val url:ArrayList<String>
)

data class creator(
        val id:Int,
        val email:String,
        val name:String,
        val profileUrl:String
)

data class comments(
        val commentId:Int,
        val boardId:Int,
        val content:String,
        @SerializedName("writer") val writer: writer
)

data class writer(
        val id:Int,
        val email:String,
        val name:String,
        val profileUrl:String
)