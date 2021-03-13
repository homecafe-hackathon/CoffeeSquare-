package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.src.main.mypage.models.MyFeedResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyLikeResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageEditResponse
import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse

interface MyPageView {
    fun onGetProfileSuccess(response: MyPageResponse)

    fun onGetProfileFailure(message: String)

    fun onPutProfileSuccess(response: MyPageEditResponse)

    fun onPutProfileFailure(message: String)

    fun onGetMyFeedSuccess(response: MyFeedResponse)

    fun onGetMyFeedFailure(message: String)

    fun onGetMyLikeSuccess(response: MyLikeResponse)

    fun onGetMyLikeFailure(message: String)
}