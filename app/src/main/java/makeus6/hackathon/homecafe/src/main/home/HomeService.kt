package com.softsquared.template.kotlin.src.main.home

import makeus6.hackathon.homecafe.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.PostSignUpRequest
import com.softsquared.template.kotlin.src.main.home.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.home.models.UserResponse
import makeus6.hackathon.homecafe.src.main.feed.AddPhotoRetrofitInterface
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeFragmentView) {


    fun getFeed(lastBoardId:Int,size:Int){
        val HomeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        HomeRetrofitInterface.getFeed(lastBoardId,size).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                view.onGetFeedSuccess(response.body() as HomeResponse)
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                view.onGetFeedFailure(t.message ?: "통신 오류")
            }
        })
    }
}
