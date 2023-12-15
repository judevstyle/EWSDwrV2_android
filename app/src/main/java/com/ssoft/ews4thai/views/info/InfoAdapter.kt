package com.ssoft.ews4thai.views.info

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.ItemInfoChilBinding
import com.ssoft.ews4thai.databinding.ItemInfoParentBinding


data class InfoData(
    var title:String = "",
    var desc:ArrayList<String> = ArrayList(),
    var type:Int = 1,
    var isExpanded:Boolean = false,

    )
class InfoAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1

    var dataItem: ArrayList<InfoData>? = null
//    var onClickListener: ((AppointData) -> Unit)? = null
//    var onClickListenerDesc: ((AppointData) -> Unit)? = null
//
////    var onClickPayListener: ((AppointData) -> Unit)? = null

    var indexBottomBG = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflate = LayoutInflater.from(parent.context)
        if (viewType == 1) {
            val binding = ItemInfoParentBinding.inflate(inflate, parent, false)
            return AppointViewHolder(binding)
        } else {
            val binding = ItemInfoChilBinding.inflate(inflate, parent, false)
            return ChildViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        if (holder is AppointViewHolder) {
//            val data = dataItem!!.get(position)
//            holder.bind(data!!)
////            holder.itemsView.root.setOnClickListener {
////                onClickListener?.invoke(data)
////            }
//        }
        val dataList = dataItem!!.get(position)
        if (dataList.type == 1) {
            holder as AppointViewHolder
            holder.apply {
                bind(dataList)
//                parentTV?.text = dataList.parentTitle
                itemsView?.root?.setOnClickListener {
                    LogUtil.showLogError("err dddd", "lddldl")
                    expandOrCollapseParentItem(dataList, position)
                }

            }

        } else {

            holder as ChildViewHolder

            holder.apply {
                val singleService = dataList.desc.first()
                bind(dataList.desc, singleService)

//                if ()

//                childTV?.text =singleService.childTitle
            }
        }
    }

    fun setOpenExpanded(data: InfoData, position: Int) {

        expandOrCollapseParentItem(data, position)

    }


    fun setItem(data: ArrayList<InfoData>) {

        dataItem = data
        notifyDataSetChanged()

    }


    private fun expandOrCollapseParentItem(singleBoarding: InfoData, position: Int) {

        LogUtil.showLogError("err", "${singleBoarding.isExpanded}")
        if (singleBoarding.isExpanded) {

            collapseParentRow(position)
        } else {
            expandParentRow(position)
        }
    }

    private fun expandParentRow(position: Int) {
        val currentBoardingRow = dataItem!!.get(position) //[position]
        val services = currentBoardingRow.desc
        currentBoardingRow.isExpanded = true
        var nextPosition = position
        if (currentBoardingRow.type == 1) {

            services.forEach { service ->
                val parentModel = InfoData()
                parentModel.type = 2
                val subList: ArrayList<String> = ArrayList()
                subList.add(service)
                parentModel.desc = subList
                dataItem!!.add(++nextPosition, parentModel)
            }
            indexBottomBG.add(nextPosition)
            LogUtil.showLogError("position", "${nextPosition}")

            notifyDataSetChanged()
        }
    }

    private fun collapseParentRow(position: Int) {


        try {
            val currentBoardingRow = dataItem!!.get(position) //[position]
            val services = currentBoardingRow.desc
            dataItem!!.get(position).isExpanded = false
            if (dataItem!!.get(position).type == 1) {
                services.forEach { _ ->
                    dataItem!!.removeAt(position + 1)
                }
                indexBottomBG.removeAll { it == position + 1 }

                notifyDataSetChanged()
            }
        } catch (ex: Exception) {

        }

    }


    override fun getItemViewType(position: Int): Int = dataItem!!.get(position).type


    override fun getItemCount(): Int {
        return dataItem?.let {
            it.size
        } ?: kotlin.run {
            0
        }
    }

    inner class LoadingViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView)

    inner class AppointViewHolder(val itemsView: ItemInfoParentBinding) :
        RecyclerView.ViewHolder(itemsView.root) {


        fun bind(data: InfoData) {

            itemsView.apply {

                titleTV.text = "${data.title}"
                if (data.isExpanded)
                    facView.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                else
                    facView.setImageResource(R.drawable.baseline_navigate_next_24)

            }
//            itemsView.customer = data
//            itemsView.icon.setImageResource(data.icon)


        }


    }


    inner class ChildViewHolder(val itemsView: ItemInfoChilBinding) :
        RecyclerView.ViewHolder(itemsView.root) {

        fun bind(list: List<String>, data: String) {

            itemsView.apply {


                titleTxt.text = "${data}"

            }

        }


    }


}