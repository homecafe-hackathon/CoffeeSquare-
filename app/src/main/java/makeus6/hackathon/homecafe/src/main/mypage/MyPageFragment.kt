package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseFragment
import makeus6.hackathon.homecafe.databinding.FragmentMyPageBinding
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        MyPageService(this).tryGetProfile()

        val bottomsheet = MyPageLoginBottomSheet()

//        로그아웃
        binding.mypageBtnSetting.setOnClickListener {
            bottomsheet.show(fragmentManager!!, bottomsheet.tag)
        }

//       프로필 수정하기
        binding.mypageImgProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyPageEditProfileActivity::class.java)
            startActivity(intent)
        }

//        내 홈카페 보기
        binding.mypageLayoutMyhome.setOnClickListener {
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.latte_brown_button)
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.white_button)

        }

//       내 좋아요 보기
        binding.mypageLayoutHeart.setOnClickListener {
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.latte_brown_button)
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.white_button)

        }
    }

    override fun onGetProfileSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        // 프로필 이미지
        Glide.with(this)
            .load(response.data.profileUrl)
            .into(binding.mypageImgProfile)
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}