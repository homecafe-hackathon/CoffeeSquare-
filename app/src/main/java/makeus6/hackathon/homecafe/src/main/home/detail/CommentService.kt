package makeus6.hackathon.homecafe.src.main.home.detail

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.feed.AddPhotoRetrofitInterface
import makeus6.hackathon.homecafe.src.main.feed.model.FeedRequest
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService(val view:DetailActivity) {

    fun getComment(boardIdx:Int){
        val commentRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        commentRetrofitInterface.getComment(boardIdx).enqueue(object : Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                view.onGetCommentSuccess(response.body() as CommentResponse)
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                view.onGetCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun postComment(commentRequest:CommentRequest){
        val editRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        editRetrofitInterface.postComment(commentRequest).enqueue(object : Callback<CommentEditResponse> {
            override fun onResponse(call: Call<CommentEditResponse>, response: Response<CommentEditResponse>) {
                view.onPostCommentSuccess(response.body() as CommentEditResponse)
            }

            override fun onFailure(call: Call<CommentEditResponse>, t: Throwable) {
                view.onPostCommentFailure(t.message ?: "통신 오류")
            }
        })
    }


}