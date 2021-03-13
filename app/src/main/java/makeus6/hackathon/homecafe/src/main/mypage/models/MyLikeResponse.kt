package makeus6.hackathon.homecafe.src.main.mypage.models

import com.google.gson.annotations.SerializedName
import makeus6.hackathon.homecafe.config.BaseResponse

data class MyLikeResponse(
        @SerializedName("data") val data: List<DataGetMyLike>
) : BaseResponse()