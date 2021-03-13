package makeus6.hackathon.homecafe.src.main.mypage.models

import com.google.gson.annotations.SerializedName

data class PostPutProfileRequest(
        @SerializedName("name") val name: String,
        @SerializedName("profileUrl") val profileUrl: String
)