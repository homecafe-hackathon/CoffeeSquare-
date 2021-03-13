package makeus6.hackathon.homecafe.src.main.home

import makeus6.hackathon.homecafe.src.main.home.models.HomeDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeUpdateRequest
import makeus6.hackathon.homecafe.src.main.home.models.HomeUpdateResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {

    @GET("/api/v1/board/list")
    fun getFeed(@Query("lastBoardId")  lastBoardId:Int , @Query("size")  size:Int)  :Call<HomeResponse>

    @DELETE("/api/v1/board")
    fun deleteFeed(@Query("boardId") boardId:Int):Call<HomeDeleteResponse>

    @PUT("/api/v1/board")
    fun updateFeed(@Body params: HomeUpdateRequest):Call<HomeUpdateResponse>
}
