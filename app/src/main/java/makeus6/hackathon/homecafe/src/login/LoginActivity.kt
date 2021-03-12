package makeus6.hackathon.homecafe.src.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityLoginBinding
import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.login.models.SetProfileResponse
import makeus6.hackathon.homecafe.src.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {
    lateinit var accessToken:String
    lateinit var email:String
    lateinit var name:String
    var profileUrl:String?=null //프로필 사진은 없을수도있음
    private val editor: SharedPreferences.Editor = ApplicationClass.sf.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakaologin", "로그인 실패", error)
            } else if (token != null) {
                Log.i("kakaologin", "로그인 성공 ${token.accessToken}")
                accessToken = "Bearer "+token.accessToken
                showLoadingDialog(this)
                LoginService(this).tryLogin(token.accessToken)
            }
        }

//      사용자 정보 받아오기
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakaologin", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                email = user.kakaoAccount?.email!!
                name = user.kakaoAccount?.profile?.nickname!!
                profileUrl = user.kakaoAccount?.profile?.profileImageUrl

                Log.i(
                    "kakaologin", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.profileImageUrl}"
                )
            }
        }

        binding.loginBtnKakao.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    override fun onLoginSuccess(response: LoginResponse) {
        dismissLoadingDialog()
//       회원가입일때 프로필 등록 화면으로 넘어가도록 -> 프로필 등록시 토큰 헤더에 등록
        if (response.data.type=="SIGN_UP"){
            val intent = Intent(this, SetProfileActivity::class.java)

            intent.putExtra("email", email)
            intent.putExtra("name", name)
            intent.putExtra("profileUrl", profileUrl)

            startActivity(intent)
        } else {
//          토큰 등록
            editor.putString("Authorization", "Bearer "+response.data.token)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onSetProfileSuccess(response: SetProfileResponse) {
        TODO("Not yet implemented")
    }

    override fun onSetProfileFailure(message: String) {
        TODO("Not yet implemented")
    }
}