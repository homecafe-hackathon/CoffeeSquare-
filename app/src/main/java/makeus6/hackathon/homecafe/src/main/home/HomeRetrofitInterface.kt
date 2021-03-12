package makeus6.hackathon.homecafe.src.main.home

import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitInterface {
    
    @GET("/api/v1/board/list")
    fun getFeed(@Query("lastBoardId")  lastBoardId:Int , @Query("size")  size:Int)  :Call<HomeResponse>

}
