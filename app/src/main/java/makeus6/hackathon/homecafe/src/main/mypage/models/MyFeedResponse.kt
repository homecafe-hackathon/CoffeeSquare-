package makeus6.hackathon.homecafe.src.main.mypage.models

import com.google.gson.annotations.SerializedName
import makeus6.hackathon.homecafe.config.BaseResponse

data class MyFeedResponse(
        @SerializedName("data") val data: List<DataGetMyFeed>
) : BaseResponse()