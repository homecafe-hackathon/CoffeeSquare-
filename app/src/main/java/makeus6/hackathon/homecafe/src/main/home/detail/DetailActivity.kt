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
    private var state:Boolean?=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("boardidx")&&intent.hasExtra("comment")&&intent.hasExtra("content")
                &&intent.hasExtra("like")&&intent.hasExtra("profile")&&intent.hasExtra("nick")&&intent.hasExtra("url")&&intent.hasExtra("like_state"))
        {
            boardIdx=intent.getIntExtra("boardidx",0)
            comment=intent.getIntExtra("comment",0)
            like=intent.getIntExtra("like",0)
            content=intent.getStringExtra("content")
            profile=intent.getStringExtra("profile")
            nick=intent.getStringExtra("nick")
            url= intent.getStringArrayListExtra("url") as ArrayList<String>
            state=intent.getBooleanExtra("like_state",false)
        }
        binding.feedName.text=nick
        binding.commentRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.userNick.text=nick
        binding.commentCount.text="??????"+comment.toString()+"???"
        binding.likeCount.text="?????????"+like.toString()+"???"

        binding.userImg.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.feedView.adapter=ViewAdapter(this,url)

        binding.contentText.text=content
        if (profile!=null) {
            Glide.with(this).load(profile)
                    .error(Glide.with(binding.userImg).load(R.drawable.no_profile_img))
                    .apply(RequestOptions().circleCrop())
                    .into(binding.userImg)
        }


        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)

        if (state==false){
            binding.loveBtn.setOnClickListener {

                val commentLike=CommentFeedRequest(
                        boardId=boardIdx!!
                )
                binding.loveBtn.setImageResource(R.drawable.like_on)
                state=true
                CommentService(this).addLike(commentLike)
            }
        }
        else{
            binding.loveBtn.setOnClickListener {
                binding.loveBtn.setImageResource(R.drawable.love)
                state=false
                CommentService(this).deleteLike(boardIdx!!)
            }
        }

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
            binding.commentUploadBtn.visibility= View.VISIBLE
            binding.commentEdit.visibility=View.VISIBLE
        }



    }


    override fun onGetCommentSuccess(response: CommentResponse) {
        dismissLoadingDialog()
        Log.d("??????","??????:"+response.data.toString())

        binding.commentCount.text="??????"+response.data.board.commentsCount+"???"
        binding.likeCount.text="?????????"+response.data.board.likesCount+"???"
        state=response.data.board.like
        binding.commentRecycler.adapter=CommentRecycler(this,response.data)
    }

    override fun onGetCommentFailure(message: String) {
        dismissLoadingDialog()
        Log.d("??????","??????"+message)
    }

    override fun onPostCommentSuccess(response: CommentEditResponse) {
        dismissLoadingDialog()
        Log.d("??????","??????:"+response.data.toString())
        Log.d("??????","????????? ???????????????")
        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)


    }

    override fun onPostCommentFailure(message: String) {
        dismissLoadingDialog()
        Log.d("??????","??????"+message)
    }

    override fun onAddLikeSuccess(response: FeedLikeResponse) {
        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)
    }

    override fun onAddLikeFailure(message: String) {
        dismissLoadingDialog()
    }

    override fun onDeleteLikeSuccess(response: FeedLikeResponse) {
        showLoadingDialog(this)
        CommentService(this).getComment(boardIdx!!)
    }

    override fun onDeleteLikeFailure(message: String) {
        dismissLoadingDialog()
    }


}