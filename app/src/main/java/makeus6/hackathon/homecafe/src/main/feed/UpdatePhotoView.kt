package makeus6.hackathon.homecafe.src.main.feed

import makeus6.hackathon.homecafe.src.main.feed.model.FeedResponse

interface UpdatePhotoView {

    fun onUpdatePhotoSuccess(response: FeedResponse)

    fun onUpdatePhotoFailure(message: String)
}