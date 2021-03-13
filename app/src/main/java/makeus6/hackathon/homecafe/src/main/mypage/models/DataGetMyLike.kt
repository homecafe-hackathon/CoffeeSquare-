package makeus6.hackathon.homecafe.src.main.mypage.models

import com.google.gson.annotations.SerializedName

data class DataGetMyLike(
        @SerializedName("id") val id:Int=0,
        @SerializedName("description") val description: String,
        @SerializedName("likesCount") val likesCount: Int=0,
        @SerializedName("pictureUrls") val pictureUrls: List<String>
)