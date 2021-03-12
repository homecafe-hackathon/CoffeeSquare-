package makeus6.hackathon.homecafe.src.main.home.detail


import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentRetrofitInterface {

    @GET("/api/v1/board")
    fun getComment(@Query("boardId") boardId:Int): Call<CommentResponse>
}