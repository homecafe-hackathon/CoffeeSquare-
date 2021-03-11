package makeus6.hackathon.homecafe.src.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import makeus6.hackathon.homecafe.R

class FeedRecycler(val context: Context): RecyclerView.Adapter<FeedRecycler.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRecycler.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return 0
    }

    override fun onBindViewHolder(holder: FeedRecycler.ViewHolder, position: Int) {

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

}