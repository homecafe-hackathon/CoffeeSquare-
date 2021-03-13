package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityMyPageEditProfileBinding
import makeus6.hackathon.homecafe.src.main.mypage.models.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyPageEditProfileActivity : BaseActivity<ActivityMyPageEditProfileBinding>(ActivityMyPageEditProfileBinding::inflate), MyPageView {
    var storage: FirebaseStorage? = null
    private val Gallery = 1
    var photoUri: Uri? = null
    private var profileUri:String? = null
    private val REQUEST_READ_EXTERMAL_STORAGE = 1000
    private var firebaseuri :Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profileUrl = intent.getStringExtra("profileUrl")
        val name = intent.getStringExtra("name")

        Log.d("확인", profileUrl.toString())
        Log.d("확인", name.toString())

//        이미 프로필 사진이 존재하면,
        if (profileUrl != null) {
            profileUri = profileUrl

            // 프로필 이미지
            Glide.with(this)
                    .load(profileUrl)
                    .into(binding.editprofileImgProfile)
        }
//        프로필 사진이 존재하지 않았다면
        else{
            profileUri = null
        }

        storage = FirebaseStorage.getInstance()

        binding.editprofileImgProfile.setOnClickListener {
            loadImage()
        }

//       이름
        binding.editprofileEdtName.setText(name)

        Log.d("확인", profileUrl.toString())
        //Log.d("확인", name.toString())

        binding.editprofileBtnRegister.setOnClickListener {
//        프로필 수정
            showLoadingDialog(this)

            //       프로필 변경 x 원래 profile
            val postPutProfileRequest =
                    PostPutProfileRequest(binding.editprofileEdtName.text.toString(), profileUri.toString())

            Log.d("hello", postPutProfileRequest.profileUrl)
            Log.d("hello", postPutProfileRequest.name)

            MyPageService(this).tryPutProfile(postPutProfileRequest = postPutProfileRequest)
        }
    }

    //   갤러리 띄우기
    private fun loadImage() {
        Log.d("확인", "이미지 불러오는 중입니다.")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("확인","result입니다")
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==Gallery){
            if(resultCode== RESULT_OK){
                val dataUri=data?.data
                photoUri=data?.data
                try{
                    Log.d("확인","비트맵 변환전"+photoUri)
                    val bitmap: Bitmap =MediaStore.Images.Media.getBitmap(this.contentResolver,dataUri)
                    Log.d("확인", "선택한 사진은$bitmap")
                    Log.d("확인", dataUri.toString())

                }catch (e:Exception){
                    Log.d("확인","이미지 업로드 오류"+e.toString())
                }
            }
            else{
                Log.d("확인","이미지 안됨")
            }
        }
        contentUpload()
    }

    // 파이어베이스 업로드
    private fun contentUpload() {

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFile = "Image_" + timestamp + "_.jpg"
        val storageRef = storage?.reference?.child("media")?.child(imageFile)

        showLoadingDialog(this)

        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            Log.d("photo", "파이어베이스 업로드완료")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                firebaseuri = uri
                profileUri = uri.toString()

                dismissLoadingDialog()

                // 프로필 이미지
                Glide.with(this)
                        .load(uri)
                        .into(binding.editprofileImgProfile)
            }
        }
    }

    override fun onGetProfileSuccess(response: MyPageResponse) {
        TODO("Not yet implemented")
//        dismissLoadingDialog()
//        // 프로필 이미지
//        Glide.with(this)
//            .load(response.data.profileUrl)
//            .into(binding.mypageImgProfile)
//        name = response.data.name
//
//        if (response.data.profileUrl!=null){
//            profileUrl = response.data.profileUrl
//        }
    }

    override fun onGetProfileFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPutProfileSuccess(response: MyPageEditResponse) {
        dismissLoadingDialog()
        setResult(RESULT_OK)
//        수정완료하고 종료!
        finish()
    }

    override fun onPutProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
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