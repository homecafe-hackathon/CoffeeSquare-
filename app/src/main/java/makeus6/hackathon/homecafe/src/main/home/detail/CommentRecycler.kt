package makeus6.hackathon.homecafe.src.main.home.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.src.main.home.detail.models.commentData
import makeus6.hackathon.homecafe.src.main.home.detail.models.comments


class CommentRecycler (val context: Context, selectArr:commentData) : RecyclerView.Adapter<CommentRecycler.ViewHolder>(){

    private var items:MutableList<comments> = selectArr.comments
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRecycler.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentRecycler.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CommentRecycler.ViewHolder, position: Int) {


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
    }
}