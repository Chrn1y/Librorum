package com.dev.librorum.customViews

import android.content.Context
import android.graphics.Canvas
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
import android.graphics.drawable.Drawable
import com.dev.librorum.Utils.HEIGHT
import com.dev.librorum.Utils.WIDTH


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
        val author = itemView?.findViewById<TextView>(R.id.authorPart)

        fun bindCategory(book : BookData, context: Context ){
            text?.text = book.name
            author?.text = book.author
            Picasso.get()
                    .load(book.picture)
//                .resize(430, 640)
//                .fit()
//                .transform(Transformation)
                    .resize((WIDTH /2.8).toInt(), (HEIGHT /2.8).toInt())
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
        val author = itemView?.findViewById<TextView>(R.id.authorPart)
        fun bindCategory(book : BookData, context: Context ){
            text?.text = book.name
            author?.text = book.author
            Picasso.get()
                    .load(book.picture)
//                .resize(430, 640)
//                .fit()
//                .transform(Transformation)
                    .resize((WIDTH /2.8).toInt(), (HEIGHT /2.8).toInt())
                    .centerCrop()
                    .into(image)
            itemView.setOnClickListener { itemClick(book) }
        }
    }
}

class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable

    init {
        mDivider = context.resources.getDrawable(R.drawable.line_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}