package makeus6.hackathon.homecafe.src.login

import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.login.models.PostSignUpRequest
import retrofit2.Call
import retrofit2.http.*

interface LoginRetrofitInterface {
//   카카오 회원가입 or login 여부
    @GET("/api/v1/auth/kakao")
    fun getLogin(@Query("accessToken") accessToken:String): Call<LoginResponse>

//   회원가입 요청 API
    @POST("/api/v1/member")
    fun postSignUp(@Body params:PostSignUpRequest)
}