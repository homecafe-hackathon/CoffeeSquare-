package makeus6.hackathon.homecafe.src.main.feed

import retrofit2.Call
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AddPhotoRetrofitInterface {

    @POST("/api/v1/board")
    fun addFeed(@Body params: FeedRequest): Call<FeedResponse>


}