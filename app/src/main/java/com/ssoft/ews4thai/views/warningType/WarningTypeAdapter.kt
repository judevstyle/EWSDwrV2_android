package com.ssoft.ews4thai.views.warningType

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ItemWarningBinding
import com.ssoft.ews4thai.share.HandleClickListener
import java.text.DecimalFormat


class WarningTypeAdapter(context: Context, val listener: HandleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING: Int = 1
    private val VIEW_TYPE_ITEM: Int = 2
    private var STATE_ALLDEL: Boolean = false
    var action:Int = 0
    var items:ArrayList<WarningStation>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflate = LayoutInflater.from(parent.context)


        return ViewHolder(ItemWarningBinding.inflate(inflate,parent,false))

    }

//    fun setAction(action:Int){
//        this.action = action
//    }

    override fun getItemCount(): Int {
        if (items == null) return 0
        return items!!.size
    }
    //    fun setItem(items: ArrayList<PostDao>){
//        this.items!!.addAll(items)
//    }
//
    fun getItem(position:Int): WarningStation{
        return items!!.get(position)!!
    }
//
//

    fun setItem(items: List<WarningStation>){
//        if (this.items == null)
//            this.items = ArrayList()
        this.items = ArrayList(items)
        notifyDataSetChanged()

    }

//    fun removeItem(position: Int){
//        items!!.removeAt(items!!.size-1)
//
//    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {

            val data = items?.get(position)
            holder.bind(data!!)
            holder.itemView.setOnClickListener { listener.onItemClick(holder.itemView,position,1) }

        }


    }


    inner  class ViewHolder(val itemsViews: ItemWarningBinding): RecyclerView.ViewHolder(itemsViews.root) {


        fun bind(data:WarningStation) {


            val datatmp = data


            itemsViews.apply {
//                Log.e("station","${data.stn_id}")
                titleTV.text = "${data.name}"
                txt1TV.text = "ต.${datatmp.tambon} อ.${datatmp.amphoe} จ.${datatmp.province}"
//                txt2TV.text = "หมู่บ้านครอบคลุมจำนวน ${data.stn_cover} หมู่บ้าน"
                txt2TV.text = "หมู่บ้านครอบคลุมจำนวน ${data.stn_cover} หมู่บ้าน"


                //                            data.value = String.format("%.1f","${data.value}").toDouble()
                val df = DecimalFormat("0.00")
                LogUtil.showLogError("ssdw countTV","${data.rain_value}")




                countTV.text = "${data.rain_value}"

                if (data.warning_type.equals("rain")) {
                    unitTV.text = "มม."
                    txt3TV.text = "ปริมาณน้ำฝนสะสม"

                } else {
                    unitTV.text = "ม."
//                    countTV.text = "${df.format(data.warn_wl_v.toDouble())}"
                    txt3TV.text = "ระดับน้ำ"

                }

                when(data.show_status.toInt()){
                    3 -> {
                        bgView.setBackgroundResource(R.drawable.bg_red)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_tornado)
                            .into(icon);
                    }
                    2 -> {
                        bgView.setBackgroundResource(R.drawable.bg_yel1)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_thunder)
                            .into(icon);
                    }
                    1 -> {
                        bgView.setBackgroundResource(R.drawable.bg_green)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_1)
                            .into(icon);
                    }
                    else -> {
                        bgView.setBackgroundResource(R.drawable.bg_blue)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.overcast)
                            .into(icon);
                    }
                }

//                codeTV.text = "CODE : ${post!!.u_code}"
//                idTV.text = "รหัสตัวแทนจำหน่าย ${post!!.u_code_id}"
            }
        }
    }


}

  class ViewHolder(val itemsViews: ItemWarningBinding): RecyclerView.ViewHolder(itemsViews.root) {
}