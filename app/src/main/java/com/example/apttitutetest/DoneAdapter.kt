package com.example.apttitutetest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.FieldPosition

class DoneAdapter(private val context: Context, private val info: DoneFeed) : BaseAdapter() {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return position.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val startRow: View = layoutInflater.inflate(R.layout.donelist, viewGroup, false)

        startRow.findViewById<TextView>(R.id.textView8).text =
            "Attempted Questions: ${info.qAttemppted}"
        startRow.findViewById<TextView>(R.id.q_number).text = "No of Questions: ${info.qNumbers}"
        startRow.findViewById<TextView>(R.id.textView10).text = "Negative Marks: ${info.qNegetive}"
        startRow.findViewById<TextView>(R.id.textView9).text =
            "Correct Answer : ${info.qCorrectAnswer}"

        return startRow
    }

}
