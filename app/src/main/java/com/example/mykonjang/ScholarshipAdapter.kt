package com.example.mykonjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mykonjang.databinding.Row1Binding
import com.example.mykonjang.databinding.Row2Binding
import com.example.mykonjang.databinding.Row3Binding

class ScholarshipAdapter(val items:Array<ScholarData>): RecyclerView.Adapter<ScholarshipAdapter.ViewHolder>() {
    companion object{
        private const val TYPE_MONTH = 1
        private const val TYPE_CHECKLIST = 2
        private const val TYPE_SEARCH = 3
    }

    interface OnItemClickListener{
        fun OnItemClick(holder:RecyclerView.ViewHolder, view: View, data:ScholarData, position:Int)
    }
    inner class ViewHolder(val binding: Row1Binding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = Row1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return items.size
    }

}