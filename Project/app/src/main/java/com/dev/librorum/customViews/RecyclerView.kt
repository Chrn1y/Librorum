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
import com.dev.librorum.data.DBCWrapper
import com.dev.librorum.data.DBHandler
import com.dev.librorum.data.DBWrapper
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.image


class RecyclerRecommended(val context : Context, val recommended: List<BookData>, val itemClick: (BookData) -> Unit) : RecyclerView.Adapter<RecyclerRecommended.Holder>() {

    val db = DBWrapper.getInstance(context)
    val dbc = DBCWrapper.getInstance(context)
    val dataList = db.getLargestCat(dbc)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.part_element, parent, false)
                return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(recommended[position], context)
    }

    inner class Holder(itemView: View?, val itemClick: (BookData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val image = itemView?.findViewById<ImageView>(R.id.imagePart)
        val text = itemView?.findViewById<TextView>(R.id.textPart)

        fun bindCategory(book : BookData, context: Context ){
            text?.text = book.name
            Picasso.get()
                    .load(book.picture)
                    .resize(430, 640)
                    //.fit()
                    .centerCrop()
                    .into(image)
            itemView.setOnClickListener { itemClick(book) }
        }
    }
}


class RecyclerSorted(val context : Context, val recommended: List<BookData>, val itemClick: (BookData) -> Unit) : RecyclerView.Adapter<RecyclerSorted.Holder>() {

    val db = DBWrapper.getInstance(context)
    val dataList = db.listLikes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.part_element, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindCategory(recommended[position], context)
    }

    inner class Holder(itemView: View?, val itemClick: (BookData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val image = itemView?.findViewById<ImageView>(R.id.imagePart)
        val text = itemView?.findViewById<TextView>(R.id.textPart)

        fun bindCategory(book : BookData, context: Context ){
            text?.text = book.name
            Picasso.get()
                    .load(book.picture)
                    .resize(290, 400)
                    //.fit()
                    .centerCrop()
                    .into(image)
            itemView.setOnClickListener { itemClick(book) }
        }
    }
}