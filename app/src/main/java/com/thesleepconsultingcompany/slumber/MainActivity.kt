package com.thesleepconsultingcompany.slumber

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesleepconsultingcompany.slumber.adapters.DiaryAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val list = arrayListOf("Sleep Diary", "Headache Diary")
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbarcom)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv = findViewById<TextView>(R.id.tvtitle)
        tv.text = "Diaries"

        recycleMenu.setHasFixedSize(true)
        recycleMenu.layoutManager = LinearLayoutManager(this)
        val menuAdapter =
            DiaryAdapter(
                this,
                list
            )
        recycleMenu.adapter = menuAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> { finish(); return true }
        }
        return super.onOptionsItemSelected(item)
    }
}
