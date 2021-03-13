package makeus6.hackathon.homecafe.src.main.mypage

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import org.jetbrains.anko.padding

class MyFeedAdapter(val context: Context, uriArr: ArrayList<String>) : BaseAdapter(){
    private var items = ArrayList<String>()

    init {
        this.items = uriArr
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(p: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        val display = context.resources.displayMetrics
        imageView.padding = 2
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = LinearLayout.LayoutParams(display.widthPixels / 3,
            display.widthPixels / 3)
        Glide.with(context).load(items[p]).into(imageView)
        return imageView
    }
}