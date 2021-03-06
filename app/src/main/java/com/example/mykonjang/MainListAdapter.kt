package com.example.mykonjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter(private var items: MutableList<ScholarData>): RecyclerView.Adapter<MainListAdapter.ViewHolder> () {
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var scholarList: TextView = itemView!!.findViewById(R.id.scholarText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.scholarList.text = items[position].scholarName + " : " + items[position].scholarList
    }

    override fun getItemCount(): Int {
        return items.size
    }
}