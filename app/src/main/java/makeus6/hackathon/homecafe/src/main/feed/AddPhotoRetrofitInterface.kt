package makeus6.hackathon.homecafe.src.main.feed

import retrofit2.Call
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import makeus6.hackathon.homecafe.src.main.feed.model.FeedUpdateRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AddPhotoRetrofitInterface {

    @POST("/api/v1/board")
    fun addFeed(@Body params: FeedRequest): Call<FeedResponse>

    @PUT("/api/v1/board")
    fun updateFeed(@Body params:FeedUpdateRequest):Call<FeedResponse>
}