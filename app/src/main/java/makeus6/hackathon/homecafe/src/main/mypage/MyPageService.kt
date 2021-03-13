package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.mypage.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageView) {
//    프로필 조회
    fun tryGetProfile(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getProfile().enqueue(object : Callback<MyPageResponse>{
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                view.onGetProfileSuccess(response.body() as MyPageResponse)
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                view.onGetProfileFailure(t.message ?: "통신 오류")
            }
        })
    }

//    프로필 수정
    fun tryPutProfile(postPutProfileRequest: PostPutProfileRequest){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.putProfile(postPutProfileRequest).enqueue(object : Callback<MyPageEditResponse>{
            override fun onResponse(call: Call<MyPageEditResponse>, response: Response<MyPageEditResponse>) {
                view.onPutProfileSuccess(response.body() as MyPageEditResponse)
            }

            override fun onFailure(call: Call<MyPageEditResponse>, t: Throwable) {
                view.onPutProfileFailure(t.message ?: "통신 오류")
            }
        })
    }

//    내가 올린 피드 조회
    fun tryGetMyFeed(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getMyFeed().enqueue(object : Callback<MyFeedResponse>{
            override fun onResponse(call: Call<MyFeedResponse>, response: Response<MyFeedResponse>) {
                view.onGetMyFeedSuccess(response.body() as MyFeedResponse)
            }

            override fun onFailure(call: Call<MyFeedResponse>, t: Throwable) {
                view.onGetMyFeedFailure(t.message ?: "통신 오류")
            }
        })
    }

    //    내가 좋아요한 피드 조회
    fun tryGetMyLike(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getMyLike().enqueue(object : Callback<MyLikeResponse>{
            override fun onResponse(call: Call<MyLikeResponse>, response: Response<MyLikeResponse>) {
                view.onGetMyLikeSuccess(response.body() as MyLikeResponse)
            }

            override fun onFailure(call: Call<MyLikeResponse>, t: Throwable) {
                view.onGetMyLikeFailure(t.message ?: "통신 오류")
            }
        })
    }
}