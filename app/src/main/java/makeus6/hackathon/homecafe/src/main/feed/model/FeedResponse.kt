package makeus6.hackathon.homecafe.src.main.feed.model

import com.google.gson.annotations.SerializedName

class FeedResponse (
        @SerializedName("code") val code:String,
        @SerializedName("message") val message:String

)