package com.thesleepconsultingcompany.slumber.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.thesleepconsultingcompany.slumber.R
import com.thesleepconsultingcompany.slumber.ui.sleep.BeforeSleepActivity
import com.thesleepconsultingcompany.slumber.ui.sleep.NapActivity
import com.thesleepconsultingcompany.slumber.ui.sleep.WakeupActivity
import kotlinx.android.synthetic.main.activity_sleep.*
import java.util.*

class SleepActivity : AppCompatActivity() {
    lateinit var date:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        val toolbar = findViewById<Toolbar>(R.id.toolbarcom)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv = findViewById<TextView>(R.id.tvtitle)
        tv.text = "Sleep Diary"

        val myCalendar = Calendar.getInstance()
        val year = myCalendar[Calendar.YEAR]
        var month = myCalendar[Calendar.MONTH]
        val day = myCalendar[Calendar.DAY_OF_MONTH]
        month += 1
        var mon = if (month < 10) "0$month" else month.toString()
        val d = if(day < 10) "0$day" else day.toString()
        dateMain.text = "$d-$mon-$year"
        month-=1
        dateMain.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var month = month
                        month = month + 1
                        val day: String
                        var mon = if (month < 10) "0$month" else month.toString()
                        day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                        date = "$day-$mon-$year"
                        dateMain.text = date
                    }, year, month, day
                )
            datePickerDialog.show()

        }

        val speedDialView = findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_morning, R.drawable.ic_edit_white_24dp)
                .setLabel("Wake Up entry")
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.torq, theme))
                .create())
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_night, R.drawable.ic_edit_white_24dp)
                .setLabel("Before Sleep Entry")
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.torq, theme))
                .create())
        speedDialView.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_evening, R.drawable.ic_edit_white_24dp)
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.torq, theme))
                .setLabel("Nap Entry")
                .create())

        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_morning -> {
                    startActivity(Intent(this,
                        WakeupActivity::class.java))
                    speedDialView.close() // To close the Speed Dial with animation
                    return@OnActionSelectedListener true // false will close it without animation
                }
                R.id.fab_night -> {
                    startActivity(Intent(this,
                        BeforeSleepActivity::class.java))
                    speedDialView.close()
                    return@OnActionSelectedListener true
                }
                R.id.fab_evening -> {
                    startActivity(Intent(this,
                        NapActivity::class.java))
                    speedDialView.close()
                    return@OnActionSelectedListener true
                }
            }
            false
        })
    }
}
