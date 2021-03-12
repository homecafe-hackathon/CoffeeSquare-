package makeus6.hackathon.homecafe.src.main.feed

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityAddphotoBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPhotoActivity : BaseActivity<ActivityAddphotoBinding>(ActivityAddphotoBinding::inflate) {

    var storage: FirebaseStorage? = null
    val Gallery = 0
    private var selectUri: Uri? = null
    var photoUri: Uri? = null
    private val REQUEST_READ_EXTERMAL_STORAGE = 1000
    val uriArr = ArrayList<String>()
    val selectArr = ArrayList<String>()
    var sub_img: String? = null
    private val check: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FirebaseStorage.getInstance()


        binding.uploadBtn.setOnClickListener { loadImage() }


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
//                val count = data!!.clipData!!.itemCount
//
//                for (i in 0 until count) {
//                    val imageUri = data.clipData!!.getItemAt(i).uri
//                    uriArr.add(imageUri.toString())
//                }
                var dataUri = data?.data
                photoUri = data?.data
                try {
                    Log.d("확인", "비트맵 변환전" + photoUri)
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                    Log.d("확인", "선택한 사진은" + bitmap)
                    Log.d("확인", dataUri.toString())
                    binding.selectImg.setImageBitmap(bitmap)
                   // binding.selectImg.setImageURI(data.clipData!!.getItemAt(0).uri)
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
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFile = "Image_" + timestamp + "_.jpg"
        val storageRef = storage?.reference?.child("media")?.child(imageFile)

        showLoadingDialog(this)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            Log.d("photo", "파이어베이스 업로드완료")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                selectUri = uri
                Log.d("확인", uri.toString())
                Log.d("확인", uri.toString())
                val check: String = uri.toString()
                val check_uri: Uri = Uri.parse(check)
                Log.d("확인", "문자열된값:" + check)
                Log.d("확인", "Uri로 다시 변환된값:" + check_uri)
                dismissLoadingDialog()

            }
        }
    }

}