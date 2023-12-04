package com.ssoft.ews4thai.views.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ssoft.ews4thai.databinding.ItemStringViewBinding
import com.ssoft.ews4thai.share.HandleClickListener

class StringAdapter(
    context: Context, val listener: HandleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var items: List<String>? = null
    var state_search = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflate = LayoutInflater.from(parent.context)
        val view = ItemStringViewBinding.inflate(inflate, parent, false)
        return ViewHolder(view)

    }


    override fun getItemCount(): Int {

            if (items == null) return 0
            return items!!.size



    }

    //
    fun getItem(position: Int): String {
        return items!!.get(position)!!
    }
//
//

    fun setItem(items: List<String>) {
        this.items = items

    }

    fun resetSTATE() {
        state_search = false
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {

                val data = items?.get(position)
                holder.bind(data!!)
                holder.itemView.setOnClickListener {
                    listener.onItemClick(
                        holder.itemView,
                        position,
                        1
                    )
                }


        }


    }


    class ViewHolder(val itemsViews: ItemStringViewBinding) : RecyclerView.ViewHolder(itemsViews.root) {


        fun bind(data: String) {

            itemsViews.apply {
                titleTV.text = "${data}"
            }

        }
    }


}