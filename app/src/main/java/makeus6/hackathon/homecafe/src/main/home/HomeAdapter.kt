package makeus6.hackathon.homecafe.src.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.src.main.home.models.creator
import makeus6.hackathon.homecafe.src.main.home.models.data


class HomeAdapter(val context: Context, selectArr:MutableList<data>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val items:MutableList<data> = selectArr

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return HomeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        var profileUrl=items[position].creator[position].profileUrl
        if(profileUrl!!.contains("alt=")){
            profileUrl=profileUrl.split("alt=").first()
            profileUrl=profileUrl.substring(0,profileUrl.length-1)
            profileUrl+="?alt=media"
            Log.d("Url",profileUrl.toString())
        }

        holder.user_img.scaleType=ImageView.ScaleType.CENTER_CROP
        if(items[position].creator[position].profileUrl.isNotEmpty()){
            Glide.with(context).load(profileUrl)
                    .error(Glide.with(holder.user_img).load(R.drawable.no_img))
                    .apply(RequestOptions().circleCrop())
                    .into(holder.user_img)
        }

        holder.feed_viewpager.adapter=ViewAdapter(context,items[position].board[position].url)

        holder.comment_count.setText("${items[position].board[position].commentsCount} 개")
        holder.like_count.setText("${items[position].board[position].likesCount} 개")
        holder.user_nick.text=items[position].creator[position].name

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var user_img=itemView.findViewById<ImageView>(R.id.user_img)
        var user_nick=itemView.findViewById<TextView>(R.id.user_nick)
        var feed_viewpager=itemView.findViewById<ViewPager2>(R.id.feed_view)
        var like_count=itemView.findViewById<TextView>(R.id.likeCount)
        var comment_count=itemView.findViewById<TextView>(R.id.commentCount)

    }

}