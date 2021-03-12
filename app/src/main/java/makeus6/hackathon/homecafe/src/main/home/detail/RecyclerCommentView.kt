package makeus6.hackathon.homecafe.src.main.home.detail

import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentUpdateResponse

interface RecyclerCommentView {

    fun onDeleteCommentSuccess(response: CommentDeleteResponse)

    fun onDeleteCommentFailure(message: String)

    fun onUpdateCommentSuccess(response: CommentUpdateResponse)

    fun onUpdateCommentFailure(message: String)
}