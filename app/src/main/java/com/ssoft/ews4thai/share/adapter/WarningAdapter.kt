package com.ssoft.ews4thai.share.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ItemWarningReportBinding
import com.ssoft.ews4thai.databinding.ItemWarningReportHeaderBinding
import com.ssoft.ews4thai.share.HandleClickListener
import java.text.DecimalFormat

class WarningAdapter(context: Context, val listener: HandleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING: Int = 1
    private val VIEW_TYPE_ITEM: Int = 2
    private var STATE_ALLDEL: Boolean = false
    var formatter: DecimalFormat = DecimalFormat("0.00")
    var warningStation: WarningStation? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val inflate = LayoutInflater.from(parent.context)

            val view = ItemWarningReportHeaderBinding.inflate(inflate,parent, false)

            return ViewHolderHeader(
                view
            )
        }  else {
            val inflate = LayoutInflater.from(parent.context)

            val view = ItemWarningReportBinding.inflate(inflate,parent, false)
            return ViewHolder(
                view
            )

        }

    }

    override fun getItemCount(): Int {
        if (warningStation == null) return 0
        return 8
    }
//    fun setItem(items: ArrayList<PostDao>){
//        this.items!!.addAll(items)
//    }
//
//    fun getItem(position:Int): String{
//        return items!!.get(position)!!
//    }
//
//

    override fun getItemViewType(position: Int): Int {

        if (position == 0) return 1
        else return 2

//        return super.getItemViewType(position)
    }

//    fun setItem(items: EwsDao) {
////        if (this.items == null)
////            this.items = ArrayList()
//        this.ewsDao = items
//
//    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {

//            val data = items?.get((itemCount-1)-position)


            when (position - 1) {
                0 -> {
                    holder.bind("24", warningStation!!.rain24h)
                }
                1 -> {
                    holder.bind("48", warningStation!!.rain48h)

                }
                2 -> {
                    holder.bind("72", warningStation!!.rain72h)

                }
                3 -> {
                    holder.bind("96", warningStation!!.rain96h)

                }
                4 -> {
                    holder.bind("120", warningStation!!.rain120h)

                }
                5 -> {
                    holder.bind("144", warningStation!!.rain144h)

                }
                6 -> {
                    holder.bind("168", warningStation!!.rain168h)

                }

            }

//            holder.bind("")
//            holder.itemView.setOnClickListener { listener.onItemClick(holder.itemView,position,1) }


        } else if (holder is ViewHolderHeader) {

            val h = holder as ViewHolderHeader
            h.bind(warningStation!!)


        }


    }


    inner class ViewHolder(val itemsViews: ItemWarningReportBinding) : RecyclerView.ViewHolder(itemsViews.root) {


        fun bind(data: String, number: String) {


//            val datatmp = data.data!!

//            Log.e("dds","${data.data!!}")
            itemsViews.apply {
                houseTV.text = "ฝน ${data} ชั่วโมง"

                try {
                    numberTV.text = "${formatter.format(number.toDouble())}"

                } catch (exx: Exception) {

                    numberTV.text = "0.00"

                }

            }

        }
    }


    inner class ViewHolderHeader(val itemsViews: ItemWarningReportHeaderBinding) : RecyclerView.ViewHolder(itemsViews.root) {


        fun bind(data: WarningStation) {


//            val datatmp = data.data!!

//            Log.e("dds","${data.data!!}")
            itemsViews.apply {

//                val station = data.data!!
                titleTV.text = "${data.name}"
                descTV.setText("ต. ${data.tambon} อ.${data.amphoe} จ.${data.province}");
                desc2TV.setText("หมู่บ้านครอบคลุมจำนวน ${data.stn_cover} หมู่บ้าน");


                if (data.show_status == 9 || data.show_status == -999 || data.show_status == 0) {
                    totalTV.setText(data.rain12h + "")
                }else
                    totalTV.text = "${data.rain_value}"


//                titleTypeTV
//                if (data.warning_type.equals("rain"))
                    titleTypeTV.text = "ปริมาณฝนสะสม"
//                else
//                    titleTypeTV.text = "ระดับน้ำ"


//                titleTV







                when (data.show_status.toInt()) {

                    3 -> {
//                        bgView.setBackgroundResource(R.drawable.bg_red)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_tornado)
                            .into(icon);
                    }

                    2 -> {
//                        bgView.setBackgroundResource(R.drawable.bg_yel)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_thunder)
                            .into(icon);
                    }

                    1 -> {
//                        bgView.setBackgroundResource(R.drawable.bg_green)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.rain_1)
                            .into(icon);
                    }
                    4 -> {
//                        bgView.setBackgroundResource(R.drawable.bg_blue)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.overcast)
                            .into(icon);
                    }
                    else -> {
//                        bgView.setBackgroundResource(R.drawable.bg_blue)
                        Glide.with(itemsViews.root.context)
                            .load(R.drawable.overcast)
                            .into(icon);
                    }
                }

            }

        }
    }


}