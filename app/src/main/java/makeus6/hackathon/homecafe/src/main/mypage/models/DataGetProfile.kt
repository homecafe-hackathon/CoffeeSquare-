package makeus6.hackathon.homecafe.src.main.mypage.models

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class DataGetProfile(
        @SerializedName("id") val id:Int=0,
        @SerializedName("email") val email: String,
        @SerializedName("name") val name: String,
        @Nullable @SerializedName("profileUrl") val profileUrl: String?
)