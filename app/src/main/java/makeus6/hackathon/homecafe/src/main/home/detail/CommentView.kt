package makeus6.hackathon.homecafe.src.main.home.detail

import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentEditResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentUpdateResponse

interface CommentView {


    fun onGetCommentSuccess(response: CommentResponse)

    fun onGetCommentFailure(message: String)

    fun onPostCommentSuccess(response: CommentEditResponse)

    fun onPostCommentFailure(message: String)


}