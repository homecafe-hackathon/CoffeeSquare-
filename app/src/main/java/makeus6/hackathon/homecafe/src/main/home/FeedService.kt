package makeus6.hackathon.homecafe.src.main.home

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.home.detail.CommentRetrofitInterface
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentEditResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentRequest
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeUpdateRequest
import makeus6.hackathon.homecafe.src.main.home.models.HomeUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedService(val view:FeedRecyclerView) {

    fun deleteFeed(boardIdx:Int){
        val feedRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        feedRetrofitInterface.deleteFeed(boardIdx).enqueue(object : Callback<HomeDeleteResponse> {
            override fun onResponse(call: Call<HomeDeleteResponse>, response: Response<HomeDeleteResponse>) {
                view.onDeleteFeedSuccess(response.body() as HomeDeleteResponse)
            }

            override fun onFailure(call: Call<HomeDeleteResponse>, t: Throwable) {
                view.onDeleteFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun updateFeed(feedRequest: HomeUpdateRequest){
        val editRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        editRetrofitInterface.updateFeed(feedRequest).enqueue(object : Callback<HomeUpdateResponse> {
            override fun onResponse(call: Call<HomeUpdateResponse>, response: Response<HomeUpdateResponse>) {
                view.onUpdateFeedSuccess(response.body() as HomeUpdateResponse)
            }

            override fun onFailure(call: Call<HomeUpdateResponse>, t: Throwable) {
                view.onUpdateFeedFailure(t.message ?: "통신 오류")
            }
        })
    }


}