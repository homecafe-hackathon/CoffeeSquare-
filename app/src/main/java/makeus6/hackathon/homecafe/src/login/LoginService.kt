package makeus6.hackathon.homecafe.src.login

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.login.models.PostSetProfileRequest
import makeus6.hackathon.homecafe.src.login.models.SetProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginView) {

    fun tryLogin(accessToken:String){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.getLogin(accessToken).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                view.onLoginSuccess(response.body() as LoginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.onLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySetProfile(postSetProfileRequest: PostSetProfileRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postSignUp(postSetProfileRequest).enqueue(object : Callback<SetProfileResponse>{
            override fun onResponse(call: Call<SetProfileResponse>, response: Response<SetProfileResponse>) {
                view.onSetProfileSuccess(response.body() as SetProfileResponse)
            }

            override fun onFailure(call: Call<SetProfileResponse>, t: Throwable) {
                view.onSetProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}
