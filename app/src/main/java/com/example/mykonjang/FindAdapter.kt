package com.example.mykonjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FindAdapter (private var items: MutableList<ScholarData>): RecyclerView.Adapter<FindAdapter.ViewHolder> () {
    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View, data: ScholarData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var findText: TextView = itemView!!.findViewById(R.id.findText)
        var findCheckBox: CheckBox = itemView!!.findViewById(R.id.findCheckBox)

        init {
            findCheckBox.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.findText.text = items[position].scholarName
        holder.findCheckBox.isChecked = items[position].like == true
    }

    override fun getItemCount(): Int {
        return items.size
    }
}