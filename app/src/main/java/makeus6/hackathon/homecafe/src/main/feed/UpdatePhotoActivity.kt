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
import makeus6.hackathon.homecafe.databinding.ActivityUpdateBinding
import makeus6.hackathon.homecafe.src.main.MainActivity
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import makeus6.hackathon.homecafe.src.main.feed.model.FeedUpdateRequest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdatePhotoActivity : BaseActivity<ActivityUpdateBinding>(ActivityUpdateBinding::inflate), UpdatePhotoView {
    private var count: Int = 0
    var storage: FirebaseStorage? = null
    val Gallery = 1
    private var selectUri: Uri? = null
    var photoUri: Uri? = null
    private val REQUEST_READ_EXTERMAL_STORAGE = 1000
    val uriArr = ArrayList<String>()
    val selectArr = ArrayList<String>()
    private var boardId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage = FirebaseStorage.getInstance()
        if (intent.hasExtra("id")) {
            boardId = intent.getIntExtra("id", 0)
        }

        binding.uploadBtn.setOnClickListener {
            binding.uploadBtn.visibility = View.GONE
            binding.camaraView.visibility = View.GONE
            binding.uploadBtn.setBackgroundResource(R.drawable.upload_btn_yes)
            loadImage()

        }

        binding.selectList.layoutManager = GridLayoutManager(this, 3)
        binding.plusBtn.setOnClickListener {
            binding.uploadBtn.visibility = View.GONE
            binding.camaraView.visibility = View.GONE
            binding.uploadBtn.setBackgroundResource(R.drawable.upload_btn_yes)
            loadImage()
        }


        binding.feedBtn.setOnClickListener {
            val feedRequest = FeedUpdateRequest(
                    boardId = boardId,
                    description = binding.contentEdit.text.toString(),
                    pictureList = uriArr
            )
            showLoadingDialog(this)
            UpdateService(this).updateFeed(feedRequest)
        }
    }

    private fun loadImage() {
        Log.d("??????", "????????? ???????????? ????????????.")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("??????", "result?????????")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Gallery) {
            if (resultCode == RESULT_OK) {

                var dataUri = data?.data
                photoUri = data?.data
                try {
                    val clipData: ClipData = data?.clipData!!
                    count = clipData.itemCount

                    if (clipData.itemCount > 6) {
                        showCustomToast("????????? 6????????? ?????????????????????.")
                    } else {
                        if (clipData.itemCount == 1) {
                            var dataUri = clipData.getItemAt(0).uri
                            // photoUri = data?.data
                            Log.d("??????", "????????? ?????????" + photoUri)
                            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)

                            selectArr.add(dataUri.toString())
                            binding.photoCount.setText("${count}/6")
                        } else {
                            Log.d("??????", "?????? ?????????")
                            for (i in 0..count - 1) {
                                var dataUri = clipData.getItemAt(i).uri
                                //photoUri = data?.data
                                Log.d("??????", "????????? ?????????" + dataUri)
                                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                                //val bitmap=BitmapFactory.decodeFile(photoUri.toString())

                                selectArr.add(dataUri.toString())
                                binding.photoCount.setText("${count}/6")

                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.d("??????", "????????? ????????? ??????" + e.toString())
                }
            } else {
                Log.d("??????", "????????? ??????")
            }
        }
        contentUpload(count)
    }

    fun contentUpload(count: Int) {
        uriArr.clear()
        showLoadingDialog(this)

        if (count > 1) {
            for (i in selectArr.indices) {
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                var fileN = selectArr[i].split("/").last()
                val split = fileN.split(".")
                fileN = split.first() + timestamp.split('.').first() + "." + split.last()
                val storageRef = storage?.reference?.child("media")?.child(fileN)

                storageRef?.putFile(Uri.parse(selectArr[i])!!)?.addOnSuccessListener {
                    Log.d("photo", "?????????????????? ???????????????")
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        uriArr.add(uri.toString())
                        Log.d("??????", uri.toString())
                        Log.d("??????", uri.toString())
                        val check: String = uri.toString()
                        val check_uri: Uri = Uri.parse(check)
                        Log.d("??????", "???????????????:" + check)
                        Log.d("??????", "Uri??? ?????? ????????????:" + check_uri)

                        binding.selectList.adapter = selectRecycler(this, uriArr)
                    }
                }

            }
        } else {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFile = "Image_" + timestamp + "_.jpg"
            val storageRef = storage?.reference?.child("media")?.child(imageFile)

            storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
                Log.d("photo", "?????????????????? ???????????????")
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    uriArr.add(uri.toString())
                    Log.d("??????", uri.toString())
                    Log.d("??????", uri.toString())
                    val check: String = uri.toString()
                    val check_uri: Uri = Uri.parse(check)
                    Log.d("??????", "???????????????:" + check)
                    Log.d("??????", "Uri??? ?????? ????????????:" + check_uri)

                    binding.selectList.adapter = selectRecycler(this, uriArr)
                }
            }

        }
        dismissLoadingDialog()
    }


    override fun onUpdatePhotoSuccess(response: FeedResponse) {
        dismissLoadingDialog()
//        Log.d("??????",response.message)
        showCustomToast("????????? ?????????????????????")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onUpdatePhotoFailure(message: String) {
        dismissLoadingDialog()
        Log.d("??????", message)
    }
}