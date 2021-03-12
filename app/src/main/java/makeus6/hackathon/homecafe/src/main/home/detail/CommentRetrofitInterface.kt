package makeus6.hackathon.homecafe.src.main.home.detail


import makeus6.hackathon.homecafe.src.main.home.detail.models.*
import retrofit2.Call
import retrofit2.http.*

interface CommentRetrofitInterface {

    @GET("/api/v1/board")
    fun getComment(@Query("boardId") boardId:Int): Call<CommentResponse>

    @POST("/api/v1/board/comment")
    fun postComment(@Body params:CommentRequest) :Call<CommentEditResponse>

    @DELETE("/api/v1/board/comment")
    fun deleteComment(@Query("boardId") boardId:Int,@Query("commentId") commentId:Int) :Call<CommentDeleteResponse>

    @PUT("/api/v1/board/comment")
    fun updateComment(@Query("boardId") boardId:Int,@Query("commentId") commentId:Int) : Call<CommentUpdateResponse>
}