package makeus6.hackathon.homecafe.src.main.home

import makeus6.hackathon.homecafe.src.main.home.models.HomeDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.models.HomeUpdateResponse

interface FeedRecyclerView {

    fun onDeleteFeedSuccess(response: HomeDeleteResponse)

    fun onDeleteFeedFailure(message: String)

    fun onUpdateFeedSuccess(response: HomeUpdateResponse)

    fun onUpdateFeedFailure(message: String)

}