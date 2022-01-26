package com.example.apttitutetest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class StatAdapter(private val context: Context,
                    private val AllStats: ArrayList<StatFeed>
): BaseAdapter(){
    override fun getCount(): Int {
        return AllStats.count()
    }

    override fun getItem(position: Int): Any {
        return position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val mainRow: View = layoutInflater.inflate(R.layout.itemlist, viewGroup, false)

        val statText: TextView = mainRow.findViewById(R.id.stat_text)
        val statImage: ImageView = mainRow.findViewById(R.id.stat_image)

        statText.text = AllStats[position].name
        statImage.setImageResource(AllStats[position].image)

        return mainRow
    }

}