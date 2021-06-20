package com.example.mykonjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainMonthAdapter (private var items: MutableList<ScholarData>): RecyclerView.Adapter<MainMonthAdapter.ViewHolder> () {
    interface OnItemClickListener {
        fun OnItemClick(holder: MainMonthAdapter.ViewHolder, view: View, data: ScholarData, position: Int)
    }

    var itemClickListener: MainMonthAdapter.OnItemClickListener? = null

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var monthText: TextView = itemView!!.findViewById(R.id.scholarMonth)

        init {
            itemView?.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tmp = items[position].scholarMonth.split(".")
        holder.monthText.text = tmp[0] + "/" + tmp[1] + " " + items[position].scholarName
    }

    override fun getItemCount(): Int {
        return items.size
    }
}