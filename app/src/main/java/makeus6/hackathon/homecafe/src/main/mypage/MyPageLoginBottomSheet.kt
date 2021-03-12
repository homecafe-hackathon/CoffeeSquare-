package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.login.LoginActivity

class MyPageLoginBottomSheet: BottomSheetDialogFragment() {
    private val editor: SharedPreferences.Editor = ApplicationClass.sf.edit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.mypage_bottomsheet_logout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btnLogout = view?.findViewById<Button>(R.id.mypage_btn_logout)
        btnLogout?.setOnClickListener {
            //  토큰 삭제
            editor.remove("Authorization")
            editor.apply()

//           로그인으로 이동
            val intent = Intent(
                requireContext(),
                LoginActivity::class.java
            )
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}