package makeus6.hackathon.homecafe.src.main.home.detail

import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.feed.model.FeedLikeResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentUpdateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerService(val view:RecyclerCommentView) {


    fun deleteComment(boardIdx: Int,commentId:Int){
        val deleteRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        deleteRetrofitInterface.deleteComment(boardIdx,commentId).enqueue(object : Callback<CommentDeleteResponse> {
            override fun onResponse(call: Call<CommentDeleteResponse>, response: Response<CommentDeleteResponse>) {
                view.onDeleteCommentSuccess(response.body() as CommentDeleteResponse)
            }

            override fun onFailure(call: Call<CommentDeleteResponse>, t: Throwable) {
                view.onDeleteCommentFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun updateComment(boardIdx: Int,commentId:Int){
        val updateRetrofitInterface = ApplicationClass.sRetrofit.create(CommentRetrofitInterface::class.java)
        updateRetrofitInterface.updateComment(boardIdx,commentId).enqueue(object : Callback<CommentUpdateResponse> {
            override fun onResponse(call: Call<CommentUpdateResponse>, response: Response<CommentUpdateResponse>) {
                view.onUpdateCommentSuccess(response.body() as CommentUpdateResponse)
            }

            override fun onFailure(call: Call<CommentUpdateResponse>, t: Throwable) {
                view.onUpdateCommentFailure(t.message ?: "통신 오류")
            }
        })
    }



}