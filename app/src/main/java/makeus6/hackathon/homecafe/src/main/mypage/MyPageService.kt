package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageView) {
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
}