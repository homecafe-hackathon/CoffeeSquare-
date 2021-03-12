package makeus6.hackathon.homecafe.src.main.feed.model

import com.google.gson.annotations.SerializedName

data class FeedRequest (
        @SerializedName("description") val description:String,
        @SerializedName("pictures") val pictureList :MutableList<String>
)
