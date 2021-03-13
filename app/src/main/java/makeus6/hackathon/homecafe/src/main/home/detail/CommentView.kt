package makeus6.hackathon.homecafe.src.main.home.detail

import makeus6.hackathon.homecafe.src.main.feed.model.FeedLikeResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentEditResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentResponse

interface CommentView {


    fun onGetCommentSuccess(response: CommentResponse)

    fun onGetCommentFailure(message: String)

    fun onPostCommentSuccess(response: CommentEditResponse)

    fun onPostCommentFailure(message: String)

    fun onAddLikeSuccess(response: FeedLikeResponse)

    fun onAddLikeFailure(message: String)
}