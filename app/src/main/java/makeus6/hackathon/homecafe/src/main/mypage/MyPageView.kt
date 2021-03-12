package makeus6.hackathon.homecafe.src.main.mypage

import makeus6.hackathon.homecafe.src.main.mypage.models.MyPageResponse

interface MyPageView {
    fun onGetProfileSuccess(response: MyPageResponse)

    fun onGetProfileFailure(message: String)
}