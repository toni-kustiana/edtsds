package id.co.edtslib.edtsds.list.skeleton

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerShimmerAdapter(private val layout: Int):
    RecyclerView.Adapter<RecyclerShimmerViewHolder>() {
    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)

        return RecyclerShimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerShimmerViewHolder, position: Int) {

    }

    override fun getItemCount() = count
}