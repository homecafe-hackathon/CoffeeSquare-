package makeus6.hackathon.homecafe.src.main.mypage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseFragment
import makeus6.hackathon.homecafe.databinding.FragmentMyPageBinding
import makeus6.hackathon.homecafe.src.main.mypage.models.MyFeedResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyLikeResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageEditResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {
    private lateinit var mGlideRequestManager:RequestManager
    lateinit var name:String
    private var profileUrl = ""
    private val REQUEST_EDIT_PROFILE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGlideRequestManager = Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomsheet = MyPageLoginBottomSheet()

        showLoadingDialog(requireContext())
        MyPageService(this).tryGetProfile() //프로필조회
        MyPageService(this).tryGetMyFeed() //피드 조회

//        로그아웃
        binding.mypageBtnSetting.setOnClickListener {
            bottomsheet.show(fragmentManager!!, bottomsheet.tag)
        }

//       프로필 수정하기
        binding.mypageImgProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyPageEditProfileActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("profileUrl", profileUrl)
            startActivityForResult(intent, REQUEST_EDIT_PROFILE)
        }
//
//        val adapter = MyFeedAdapter(requireContext(), picturelist)
//        binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
//        binding.mypageGridview.adapter = adapter

//        내 홈카페 보기
        binding.mypageLayoutMyhome.setOnClickListener {
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.latte_brown_button)
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.white_button)

            showLoadingDialog(requireContext())
            MyPageService(this).tryGetMyFeed()
        }

//       내 좋아요 보기
        binding.mypageLayoutHeart.setOnClickListener {
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.pink_button)
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.white_button)

            showLoadingDialog(requireContext())
            MyPageService(this).tryGetMyLike()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("확인", "result입니다")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == RESULT_OK) {
                showLoadingDialog(requireContext())
                MyPageService(this).tryGetProfile()
            }
        }
    }

    override fun onGetProfileSuccess(response: MyPageResponse) {
        dismissLoadingDialog()

        if (response.data.profileUrl!=null){
            profileUrl = response.data.profileUrl

         // 프로필 이미지
            mGlideRequestManager
                    .load(response.data.profileUrl)
                    .into(binding.mypageImgProfile)
        }
        binding.mypageTvName.text = response.data.name
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetMyFeedSuccess(response: MyFeedResponse) {
        dismissLoadingDialog()

        var picturelist = response.data
        val adapter = MyFeedAdapter(requireContext(), picturelist)

        binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
        binding.mypageGridview.adapter = adapter
    }

    override fun onGetMyFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetMyLikeSuccess(response: MyLikeResponse) {
        dismissLoadingDialog()

        var picturelist = response.data
        val adapter = MyLikeAdapter(requireContext(), picturelist)

        binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
        binding.mypageGridview.adapter = adapter    }

    override fun onGetMyLikeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

//    아래 필요없음!
    override fun onPutProfileSuccess(response: MyPageEditResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutProfileFailure(message: String) {
        TODO("Not yet implemented")
    }
}