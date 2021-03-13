package makeus6.hackathon.homecafe.src.main.home

import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse

interface HomeFragmentView {

    fun onGetFeedSuccess(response: HomeResponse)

    fun onGetFeedFailure(message: String)

    fun onSearchFeedSuccess(response: HomeResponse)

    fun onSearchFeedFailure(message: String)

}