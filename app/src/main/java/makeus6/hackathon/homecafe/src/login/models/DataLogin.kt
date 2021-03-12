package makeus6.hackathon.homecafe.src.login.models

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class DataLogin(
        @SerializedName("type") val type: String,
        @SerializedName("email") val email: String,
        @SerializedName("name") val name: String,
        @Nullable @SerializedName("token") val token: String?
)