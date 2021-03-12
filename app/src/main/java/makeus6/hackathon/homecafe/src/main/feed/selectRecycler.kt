package makeus6.hackathon.homecafe.src.main.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import makeus6.hackathon.homecafe.R

class selectRecycler(val context: Context,selectArr:MutableList<String>):RecyclerView.Adapter<selectRecycler.ViewHolder>() {

    private val items:MutableList<String> = selectArr

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): selectRecycler.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: selectRecycler.ViewHolder, position: Int) {

        var Url=items[position]
        if(Url.contains("alt=")){
            Url=Url.split("alt=").first()
            Url=Url.substring(0,Url.length-1)
            Url+="?alt=media"
            Log.d("Url",Url.toString())
        }
        holder.selectImg.scaleType=ImageView.ScaleType.CENTER_CROP
        Glide.with(context).load(Url).into(holder.selectImg)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var selectImg=itemView.findViewById<ImageView>(R.id.select_img)
    }
}