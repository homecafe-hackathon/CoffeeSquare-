package makeus6.hackathon.homecafe.src.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivitySetProfileBinding
import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.login.models.PostSetProfileRequest
import makeus6.hackathon.homecafe.src.login.models.SetProfileResponse
import makeus6.hackathon.homecafe.src.main.MainActivity

class SetProfileActivity : BaseActivity<ActivitySetProfileBinding>(ActivitySetProfileBinding::inflate), LoginView {
    private val editor: SharedPreferences.Editor = ApplicationClass.sf.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 프로필 이미지
        Glide.with(this)
            .load(intent.getStringExtra("profileUrl"))
            .into(binding.setprofileImgProfile)

        binding.setprofileEdtName.setText(intent.getStringExtra("name")+"의 카페")

        binding.setprofileBtnRegister.setOnClickListener{
            Log.d("안녕", binding.setprofileEdtName.text.toString())
            val postSetProfileRequest = PostSetProfileRequest(intent.getStringExtra("email")!!, binding.setprofileEdtName.text.toString(),

                    intent.getStringExtra("profileUrl"))


            showLoadingDialog(this)
            LoginService(this).trySetProfile(postSetProfileRequest = postSetProfileRequest)
        }
    }

    override fun onLoginSuccess(response: LoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSetProfileSuccess(response: SetProfileResponse) {
        dismissLoadingDialog()
        editor.putString("Authorization", "Bearer "+response.data)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}