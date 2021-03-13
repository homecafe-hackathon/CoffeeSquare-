package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.src.main.mypage.models.*
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {
    //  정보 불러오는 api
    @GET("/api/v1/member")
    fun getProfile(): Call<MyPageResponse>

//    프로필 수정 api
    @PUT("/api/v1/member")
    fun putProfile(@Body params: PostPutProfileRequest): Call<MyPageEditResponse>

//   내가 업로드한 피드 리스트 조회
    @GET("/api/v1/board/list/my")
    fun getMyFeed() : Call<MyFeedResponse>

//    내가 좋아요 누른 피드 리스트 조회
    @GET("/api/v1/board/list/mylike")
    fun getMyLike() : Call<MyLikeResponse>
}