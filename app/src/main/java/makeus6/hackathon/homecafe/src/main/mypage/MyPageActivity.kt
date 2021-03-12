package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityMyPageBinding
import makeus6.hackathon.homecafe.src.login.LoginActivity


class MyPageActivity : BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {
    private val editor: SharedPreferences.Editor = ApplicationClass.sf.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mypageBtnLogout.setOnClickListener {
//           토큰 삭제
            editor.remove("Authorization")
            editor.apply()

//           로그인으로 이동
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}