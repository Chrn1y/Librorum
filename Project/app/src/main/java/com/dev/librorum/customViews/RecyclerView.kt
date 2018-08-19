package com.dev.librorum.customViews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.R
import com.dev.librorum.data.BookData
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.find


class RecyclerRecommended(val context : Context, val recommended: List<BookData>) : RecyclerView.Adapter<RecyclerRecommended.Holder>() {

    private val db: DBHandler? = DBHandler(context)
    val usrDataList = db!!.listBooks("%")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.part_recommended, parent, false)
                return Holder(view)
    }

    override fun getItemCount(): Int {
        return usrDataList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindCategory(recommended[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val image = itemView?.findViewById<ImageView>(R.id.imageRecommended)
        val text = itemView?.findViewById<TextView>(R.id.textRecommended)

        fun bindCategory(recommendation : BookData, context: Context ){
            text?.text = recommendation.name
        }
    }
}