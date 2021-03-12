package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {
    //  정보 불러오는 api
    @GET("/api/v1/member")
    fun getProfile(): Call<MyPageResponse>
}