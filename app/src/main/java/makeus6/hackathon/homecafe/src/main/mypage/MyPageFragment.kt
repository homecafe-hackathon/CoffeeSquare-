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
import makeus6.hackathon.homecafe.src.main.mypage.models.*

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {
    private lateinit var mGlideRequestManager:RequestManager //글라이드 매니저
    private var like_list = listOf<DataGetMyLike>() //좋아요는 나중에 불러오니깐
    lateinit var name:String
    //lateinit var bind:ViewBinding

//    var likesize =0
//    var feedsize =0

    private var likeAdapter:MyLikeAdapter?=null
    private var feedAdapter:MyFeedAdapter?=null
    var profile_url:String?=null
    private val REQUEST_EDIT_PROFILE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGlideRequestManager = Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomsheet = MyPageLoginBottomSheet()

//        프로필이미지
        mGlideRequestManager
                .load(profile_url)
                .into(binding.mypageImgProfile)

//        로그아웃
        binding.mypageBtnSetting.setOnClickListener {
            bottomsheet.show(fragmentManager!!, bottomsheet.tag)
        }

//       프로필 수정하기
        binding.mypageImgProfile.setOnClickListener {
            val intent = Intent(requireContext(), MyPageEditProfileActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("profileUrl", profile_url)
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

            binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
            binding.mypageGridview.adapter = feedAdapter
        }

//       내 좋아요 보기
        binding.mypageLayoutHeart.setOnClickListener {
            binding.mypageLayoutHeart.setBackgroundResource(R.drawable.pink_button)
            binding.mypageLayoutMyhome.setBackgroundResource(R.drawable.white_button)

            showLoadingDialog(requireContext())
            MyPageService(this).tryGetMyLike()

            likeAdapter = MyLikeAdapter(requireContext(), like_list)

            binding.mypageGridview.numColumns = 3 // 한 줄에 3개씩
            binding.mypageGridview.adapter = likeAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        showLoadingDialog(requireContext())
        MyPageService(this).tryGetProfile() //프로필조회
        MyPageService(this).tryGetMyFeed() //피드 조회
        MyPageService(this).tryGetMyLike()
    }

//   프로필 수정 activity를 띄우고 결과값 받을때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("확인", "result입니다")

        if (requestCode == REQUEST_EDIT_PROFILE) {
                if (resultCode == RESULT_OK) {
                    fragmentManager
                            ?.beginTransaction()
                            ?.detach(this@MyPageFragment)
                            ?.attach(this@MyPageFragment)
                            ?.commit()
                }
        }
    }

    override fun onGetProfileSuccess(response: MyPageResponse) {
        dismissLoadingDialog()

         // 프로필 이미지
            mGlideRequestManager
                    .load(response.data.profileUrl)
                    .into(binding.mypageImgProfile)

        binding.mypageTvName.text = response.data.name
        name = response.data.name
        profile_url = response.data.profileUrl
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetMyFeedSuccess(response: MyFeedResponse) {
        dismissLoadingDialog()

        val myfeedlist = response.data
        binding.mypageTvHomecount.text = myfeedlist.size.toString()
        feedAdapter = MyFeedAdapter(requireContext(), myfeedlist)

        binding.mypageGridview.numColumns = 3
        binding.mypageGridview.adapter = feedAdapter
    }

    override fun onGetMyFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetMyLikeSuccess(response: MyLikeResponse) {
        dismissLoadingDialog()

        val likelist = response.data
        binding.mypageTvHeartcount.text = likelist.size.toString()
        like_list = likelist
    }

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