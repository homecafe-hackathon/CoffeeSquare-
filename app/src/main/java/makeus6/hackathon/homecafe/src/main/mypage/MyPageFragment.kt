package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseFragment
import makeus6.hackathon.homecafe.databinding.FragmentMyPageBinding
import makeus6.hackathon.homecafe.src.main.mypage.models.MyFeedResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyLikeResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageEditResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse
import java.util.ArrayList

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {
    lateinit var name:String
    var profileUrl = ""
    //private lateinit var picturelist : ArrayList<String> //사진리스트

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())

        val bottomsheet = MyPageLoginBottomSheet()

//        로그아웃
        binding.mypageBtnSetting.setOnClickListener {
            bottomsheet.show(fragmentManager!!, bottomsheet.tag)
        }

//       프로필 수정하기
        binding.mypageImgProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyPageEditProfileActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("profileUrl", profileUrl)
            startActivity(intent)
        }
//
//        val adapter = MyFeedAdapter(requireContext(), picturelist)
//        binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
//        binding.mypageGridview.adapter = adapter

//        내 홈카페 보기
        binding.mypageLayoutMyhome.setOnClickListener {
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.latte_brown_button)
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.white_button)
        }

//       내 좋아요 보기
        binding.mypageLayoutHeart.setOnClickListener {
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.pink_button)
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.white_button)
        }
    }

    override fun onGetProfileSuccess(response: MyPageResponse) {
        dismissLoadingDialog()
        // 프로필 이미지
        Glide.with(this)
            .load(response.data.profileUrl)
            .into(binding.mypageImgProfile)
        name = response.data.name

        if (response.data.profileUrl!=null){
            profileUrl = response.data.profileUrl
        }
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPutProfileSuccess(response: MyPageEditResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutProfileFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetMyFeedSuccess(response: MyFeedResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetMyFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetMyLikeSuccess(response: MyLikeResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetMyLikeFailure(message: String) {
        TODO("Not yet implemented")
    }
}