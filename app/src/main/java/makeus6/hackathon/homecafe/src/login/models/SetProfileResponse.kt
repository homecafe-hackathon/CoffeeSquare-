package makeus6.hackathon.homecafe.src.login.models

import com.google.gson.annotations.SerializedName
import makeus6.hackathon.homecafe.config.BaseResponse

data class SetProfileResponse(
        @SerializedName("data") val data: String
) : BaseResponse()