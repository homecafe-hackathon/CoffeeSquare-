package makeus6.hackathon.homecafe.src.main.home.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.ApplicationClass
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentDeleteResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.CommentUpdateResponse
import makeus6.hackathon.homecafe.src.main.home.detail.models.commentData
import makeus6.hackathon.homecafe.src.main.home.detail.models.comments


class CommentRecycler (val context: Context, selectArr:commentData) : RecyclerView.Adapter<CommentRecycler.ViewHolder>(),RecyclerCommentView{

    private var items:MutableList<comments> = selectArr.comments
    private val bottomSheetDialog= BottomSheetDialog(context,R.style.BottomSheetDialogTheme)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRecycler.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentRecycler.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CommentRecycler.ViewHolder, position: Int) {

        //작성자 본인일경우


            holder.view.setOnClickListener {
                Log.d("확인",items[position].writer.email+"이메일:"+ApplicationClass.sf.getString("email",""))
                if(items[position].writer.email==(ApplicationClass.sf.getString("email",""))) {
                    val view = LayoutInflater.from(context).inflate(R.layout.my_bottom_sheet_layout, null)
                    view.findViewById<Button>(R.id.btn_update).setOnClickListener {
                        bottomSheetDialog.dismiss()
                    }
                    view.findViewById<Button>(R.id.btn_delete).setOnClickListener {
                        notifyItemRemoved(position)
                        RecyclerService(this).deleteComment(items[position].boardId, items[position].commentId)
                        bottomSheetDialog.dismiss()
                    }
                    bottomSheetDialog.setContentView(view)
                    bottomSheetDialog.show()
                }
            }


        var profileUrl = items[position].writer.profileUrl
        Log.d("확인", "프로필" + profileUrl)
        holder.userImg.scaleType = ImageView.ScaleType.CENTER_CROP

        if (profileUrl!=null) {
            Glide.with(context).load(profileUrl)
                    .error(Glide.with(holder.userImg).load(R.drawable.no_profile_img))
                    .apply(RequestOptions().circleCrop())
                    .into(holder.userImg)
        }

        holder.userNick.text=items[position].writer.name
        holder.comment.text=items[position].content

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userImg=itemView.findViewById<ImageView>(R.id.user_img)
        var userNick=itemView.findViewById<TextView>(R.id.user_nick)
        var comment=itemView.findViewById<TextView>(R.id.comment)
        var view=itemView.findViewById<View>(R.id.comment_view)
    }

    override fun onDeleteCommentSuccess(response: CommentDeleteResponse) {
        Log.d("확인","삭제성공:"+response.data.toString())
    }

    override fun onDeleteCommentFailure(message: String) {
        Log.d("확인","실패:"+message)
    }

    override fun onUpdateCommentSuccess(response: CommentUpdateResponse) {
        Log.d("확인","업데이트성공:"+response.data.toString())
    }

    override fun onUpdateCommentFailure(message: String) {
        Log.d("확인","실패:"+message)
    }
}