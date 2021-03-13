package makeus6.hackathon.homecafe.src.main.home.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityDetailBinding
import makeus6.hackathon.homecafe.src.main.feed.model.FeedLikeResponse
import makeus6.hackathon.homecafe.src.main.home.HomeAdapter
import makeus6.hackathon.homecafe.src.main.home.HomeService
import makeus6.hackathon.homecafe.src.main.home.ViewAdapter
import makeus6.hackathon.homecafe.src.main.home.detail.models.*

class DetailActivity:BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),CommentView {

    private var boardIdx:Int?=null
    private var comment:Int?=0
    private var content:String?=null
    private var like:Int?=0
    private var profile:String?=null
    private var nick:String?=null
    private var url=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("boardidx")&&intent.hasExtra("comment")&&intent.hasExtra("content")
                &&intent.hasExtra("like")&&intent.hasExtra("profile")&&intent.hasExtra("nick")&&intent.hasExtra("url"))
        {
            boardIdx=intent.getIntExtra("boardidx",0)
            comment=intent.getIntExtra("comment",0)
            like=intent.getIntExtra("like",0)
            content=intent.getStringExtra("content")
            profile=intent.getStringExtra("profile")
            nick=intent.getStringExtra("nick")
            url= intent.getStringArrayListExtra("url") as ArrayList<String>

        }
        binding.feedName.text=nick+"의 카페"
        binding.commentRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.userNick.text=nick
        binding.commentCount.text="댓글"+comment.toString()+"개"
        binding.likeCount.text="좋아요"+like.toString()+"개"

        binding.userImg.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.feedView.adapter=ViewAdapter(this,url)

        if (profile!=null) {
            Glide.with(this).load(profile)
                    .error(Glide.with(binding.userImg).load(R.drawable.no_profile_img))
                    .apply(RequestOptions().circleCrop())
                    .into(binding.userImg)
        }


        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)

        binding.commentUploadBtn.setOnClickListener {

            binding.commentEdit.visibility=View.GONE
            binding.commentUploadBtn.visibility=View.GONE
            val commentRequest=CommentRequest(
                    boardId=boardIdx!!,
                    content=binding.commentEdit.text.toString()
            )
            binding.commentEdit.setText("")
            CommentService(this).postComment(commentRequest)
            showLoadingDialog(this)
        }
        binding.commentBtn.setOnClickListener {
           binding.commentEdit.requestFocus()
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            binding.commentUploadBtn.visibility= View.VISIBLE
            binding.commentEdit.visibility=View.VISIBLE
        }

       
    }


    override fun onGetCommentSuccess(response: CommentResponse) {
        dismissLoadingDialog()
        Log.d("확인","성공:"+response.data.toString())

        binding.commentCount.text="댓글"+response.data.board.commentsCount+"개"
        binding.likeCount.text="좋아요"+response.data.board.likesCount+"개"

        binding.commentRecycler.adapter=CommentRecycler(this,response.data)
    }

    override fun onGetCommentFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인","실패"+message)
    }

    override fun onPostCommentSuccess(response: CommentEditResponse) {
        dismissLoadingDialog()
        Log.d("확인","성공:"+response.data.toString())
        Log.d("확인","댓글이 생성됩니다")
        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)


    }

    override fun onPostCommentFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인","실패"+message)
    }

    override fun onAddLikeSuccess(response: FeedLikeResponse) {
        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)
    }

    override fun onAddLikeFailure(message: String) {

    }


}