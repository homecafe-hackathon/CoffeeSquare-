package makeus6.hackathon.homecafe.src.main.feed

import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse

interface AddPhotoView {

    fun onAddPhotoSuccess(response: FeedResponse)

    fun onAddPhotoFailure(message: String)
}