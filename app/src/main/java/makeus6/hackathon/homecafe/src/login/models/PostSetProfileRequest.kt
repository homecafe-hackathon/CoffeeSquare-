package makeus6.hackathon.homecafe.src.login.models

import com.google.gson.annotations.SerializedName

data class PostSetProfileRequest(
        @SerializedName("email") val email: String,
        @SerializedName("name") val name: String?,
        @SerializedName("profileUrl") val profileUrl: String? //프로필 사진 안넣었을때
)