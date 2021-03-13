package makeus6.hackathon.homecafe.src.main.feed

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import makeus6.hackathon.homecafe.src.main.feed.model.FeedUpdateRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPhotoService(val view:AddPhotoActivity) {

    fun addFeed(feedRequest: FeedRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(AddPhotoRetrofitInterface::class.java)
        loginRetrofitInterface.addFeed(feedRequest).enqueue(object : Callback<FeedResponse> {
            override fun onResponse(call: Call<FeedResponse>, response: Response<FeedResponse>) {
                view.onAddPhotoSuccess(response.body() as FeedResponse)
            }

            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                view.onAddPhotoFailure(t.message ?: "통신 오류")
            }
        })
    }


}