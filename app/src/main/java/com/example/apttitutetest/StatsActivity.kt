package com.example.apttitutetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_stats)

        val allStat: ArrayList<StatFeed> = ArrayList()
        allStat.add(StatFeed("Total Score",R.drawable.icon_15_trophy))
        allStat.add(StatFeed("Total Test",R.drawable.group_25))
        allStat.add(StatFeed("Previous Score",R.drawable.iconfinder_12))
        allStat.add(StatFeed("Time Taken",R.drawable.group_24))

        simpleGridView.adapter = StatAdapter(this,allStat)

        next_btn.setOnClickListener {
            val intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)
        }
    }
}