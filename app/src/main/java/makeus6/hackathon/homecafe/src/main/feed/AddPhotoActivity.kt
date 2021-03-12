package makeus6.hackathon.homecafe.src.main.feed

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityAddphotoBinding
import makeus6.hackathon.homecafe.src.main.MainActivity
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPhotoActivity : BaseActivity<ActivityAddphotoBinding>(ActivityAddphotoBinding::inflate),AddPhotoView {

    var storage: FirebaseStorage? = null
    val Gallery = 1
    private var selectUri: Uri? = null
    var photoUri: Uri? = null
    private val REQUEST_READ_EXTERMAL_STORAGE = 1000
    val uriArr = ArrayList<String>()
    val selectArr = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FirebaseStorage.getInstance()


        binding.uploadBtn.setOnClickListener {  loadImage()
            binding.uploadBtn.setBackgroundResource(R.drawable.upload_btn_yes)}

        binding.selectList.layoutManager=GridLayoutManager(this,3)
        binding.plusBtn.setOnClickListener { loadImage()

            binding.uploadBtn.setBackgroundResource(R.drawable.upload_btn_yes) }


        binding.feedBtn.setOnClickListener {
            val feedRequest=FeedRequest(
                description=binding.contentEdit.text.toString(),
                pictureList = uriArr
            )
            showLoadingDialog(this)
            AddPhotoService(this).addFeed(feedRequest)
        }
    }

    private fun loadImage() {
        Log.d("확인", "이미지 불러오는 중입니다.")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("확인", "result입니다")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Gallery) {
            if (resultCode == RESULT_OK) {

                var dataUri = data?.data
                photoUri = data?.data
                try {
                    val clipData:ClipData=data?.clipData!!
                    val count=clipData.itemCount

                    if (clipData.itemCount>6){
                        showCustomToast("사진은 6장까지 선택가능합니다.")
                    }
                    else {
                        if (clipData.itemCount == 1) {
                            var dataUri = clipData.getItemAt(0).uri
                            // photoUri = data?.data
                            Log.d("확인", "비트맵 변환전" + photoUri)
                            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)

                            selectArr.add(dataUri.toString())
                        } else {
                            Log.d("확인", "사진 여러장")
                            for (i in 0..count - 1) {
                                var dataUri = clipData.getItemAt(i).uri
                                //photoUri = data?.data
                                Log.d("확인", "비트맵 변환전" + dataUri)
                                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                                //val bitmap=BitmapFactory.decodeFile(photoUri.toString())

                                selectArr.add(dataUri.toString())
                                binding.photoCount.setText("${count}/6")
                                binding.uploadBtn.setBackgroundResource(R.drawable.upload_btn_yes)
                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.d("확인", "이미지 업로드 오류" + e.toString())
                }
            } else {
                Log.d("확인", "이미지 안됨")
            }
        }
        contentUpload()
    }

    fun contentUpload() {
        uriArr.clear()
        showLoadingDialog(this)
        for (i in selectArr.indices) {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var fileN = selectArr[i].split("/").last()
            val split = fileN.split(".")
            fileN = split.first() + timestamp.split('.').first() + "." + split.last()
            val storageRef = storage?.reference?.child("media")?.child(fileN)

            storageRef?.putFile(Uri.parse(selectArr[i])!!)?.addOnSuccessListener {
                Log.d("photo", "파이어베이스 업로드완료")
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    uriArr.add(uri.toString())
                    Log.d("확인", uri.toString())
                    Log.d("확인", uri.toString())
                    val check: String = uri.toString()
                    val check_uri: Uri = Uri.parse(check)
                    Log.d("확인", "문자열된값:" + check)
                    Log.d("확인", "Uri로 다시 변환된값:" + check_uri)
                    binding.uploadBtn.visibility= View.GONE
                    binding.camaraView.visibility=View.GONE
                    binding.selectList.adapter = selectRecycler(this, uriArr)
                }
            }

        }
        dismissLoadingDialog()
    }

    override fun onAddPhotoSuccess(response: FeedResponse) {
        dismissLoadingDialog()
//        Log.d("확인",response.message)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onAddPhotoFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인",message)
    }

}