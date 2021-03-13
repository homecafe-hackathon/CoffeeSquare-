package makeus6.hackathon.homecafe.src.main.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.src.main.home.detail.DetailActivity

class ViewAdapter(private val context: Context, private val items:List<String>):
        RecyclerView.Adapter<ViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter.ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_img,parent,false))

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: ViewAdapter.ViewHolder, position: Int) {
        var Url=items[position]
        if(Url.contains("alt=")){
            Url=Url.split("alt=").first()
            Url=Url.substring(0,Url.length-1)
            Url+="?alt=media"
            Log.d("Url",Url.toString())
        }
        var count=position+1
        if(items.size>1)
        {
            holder.text.text=count.toString()+"/"+items.size
        }
        else
            holder.text.visibility=View.INVISIBLE

        holder.imageUrl.scaleType=ImageView.ScaleType.CENTER_CROP
        Glide.with(context).load(Url).into(holder.imageUrl)


//        holder.imageUrl.setOnClickListener {
//
//        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageUrl: ImageView = view.findViewById(R.id.view_img)
        val text: TextView = view.findViewById(R.id.view_text)
    }
}