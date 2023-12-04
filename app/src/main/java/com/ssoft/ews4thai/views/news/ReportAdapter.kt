package com.ssoft.ews4thai.views.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.databinding.ItemReportBinding
import com.ssoft.ews4thai.share.HandleClickListener
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportAdapter(context: Context, val listener: HandleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING: Int = 1
    private val VIEW_TYPE_ITEM: Int = 2
    private var STATE_ALLDEL: Boolean = false

    var items:ArrayList<ReportModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)

            return ViewHolder(ItemReportBinding.inflate(inflate,parent,false))

    }


    override fun getItemCount(): Int {
        if (items == null) return 0
        return items!!.size
    }
//    fun setItem(items: ArrayList<PostDao>){
//        this.items!!.addAll(items)
//    }
//
    fun getItem(position:Int): ReportModel{
        return items!!.get(position)!!
    }
//
//

    fun setItem(items: List<ReportModel>){
        if (this.items == null)
            this.items = ArrayList()
        this.items?.clear()
        this.items!!.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items!!.removeAt(items!!.size-1)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {

            val data = items?.get(position)
            holder.bind(data!!)
            holder.itemView.setOnClickListener { listener.onItemClick(holder.itemView,position,1) }


        }


    }


    class ViewHolder(val itemsViews: ItemReportBinding): RecyclerView.ViewHolder(itemsViews.root) {


        fun bind(data:ReportModel) {


//            val datatmp = data.data!!


            val formatter: Format
            formatter = SimpleDateFormat("วันที่ d เดือน MMMM พ.ศ.", Locale("th", "TH"))
            val dmyFormat = SimpleDateFormat("yyyy-MM-dd")
            val formatNowYear = SimpleDateFormat("yyyy")

            val d: Date = dmyFormat.parse(data.report_date.split(" ")[0])
//            d.da

            itemsViews.apply {
                titleTV.text = "${splitTitle("สถานี",data.report_title)}"
                countTV.text = "${adapterPosition+1}"
                txt1TV.text = "สถานี ${splitAddr("สถานี",data.report_title)} ต.${splitAddr("ตำบล",data.report_title)} อ.${splitAddr("อำเภอ",data.report_title)} จ.${splitAddr("จังหวัด",data.report_title)}"

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    var tbd = ThaiBuddhistDate.now()
//                    tbd.format(
//                        DateTimeFormatter.ofPattern("วันที่ dd เดือน MMMM พ.ศ. yyyy"))
//                    txt2TV.text = "${ tbd.format(
//                        d.ofPattern("วันที่ dd เดือน MMMM พ.ศ. yyyy"))} เวลา ${data.date.split(" ")[1]}"
//
//                } else {
                    txt2TV.text = "${formatter.format(d)} ${formatNowYear.format(d).toInt()+543} เวลา ${data.report_date.split(" ")[1]}"

//                }


                if (splitSpace(data.report_title).equals("เตือนภัย")){

                    countTV.background.setTint(itemsViews.root.resources.getColor(R.color.yel))//  = resources.getDrawable(R.drawable.bg_yel_n)

                }else if (splitSpace(data.report_title).equals("เฝ้าระวัง")){
//                    box.background  = resources.getDrawable(R.drawable.bg_green_n)
                    countTV.background.setTint(itemsViews.root.resources.getColor(R.color.green))
                }
                else if (splitSpace(data.report_title).equals("อพยพ")){
//                    box.background  = resources.getDrawable(R.drawable.bg_red_n)
                    countTV.background.setTint(itemsViews.root.resources.getColor(R.color.red))
                }

                else {
//                    box.background  = resources.getDrawable(R.drawable.bg_blue_n)
                    countTV.background.setTint(itemsViews.root.resources.getColor(R.color.blue))
                }

            }

        }

        fun splitTitle(text:String,msg:String):String{

            return msg.split("${text}")[0]

        }

        fun splitAddr(text:String,msg:String):String{

            try {
                return (msg.split("${text}")[1]).split(" ")[1]

            }catch (ex : Exception){
                return ""
            }

        }

        fun splitSpace(msg:String):String{

            try {
                return (msg.split(" "))[1]

            }catch (ex : Exception){
                return ""
            }

        }

    }


}