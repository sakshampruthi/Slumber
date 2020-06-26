package com.thesleepconsultingcompany.slumber.ui.sleep

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thesleepconsultingcompany.slumber.R
import com.thesleepconsultingcompany.slumber.models.RecordsNap
import kotlinx.android.synthetic.main.activity_evening.*
import java.util.*

class NapActivity : AppCompatActivity() {

    lateinit var mDatabaseReference:DatabaseReference
    lateinit var date:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evening)
        val toolbar = findViewById<Toolbar>(R.id.toolbarcom)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv = findViewById<TextView>(R.id.tvtitle)
        tv.text = "Nap Entry"
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid).child("Records")

        val myCalendar = Calendar.getInstance()
        val year = myCalendar[Calendar.YEAR]
        var month = myCalendar[Calendar.MONTH]
        val day = myCalendar[Calendar.DAY_OF_MONTH]
        month += 1
        var mon = if (month < 10) "0$month" else month.toString()
        val d = if(day < 10) "0$day" else day.toString()
        dateSetEven.text = "$d-$mon-$year"
        dateSetEven.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var month = month
                        month = month + 1
                        val day: String
                        var mon = if (month < 10) "0$month" else month.toString()
                        day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                        date = "$day-$mon-$year"
                        dateSetEven.text = date
                    }, year, month, day
                )
            datePickerDialog.show()

        }


        ans1EvenTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this ,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val hour = if (hourOfDay < 10)
                        "0$hourOfDay"
                    else
                        hourOfDay.toString()
                    val min = if (minute<10)
                        "0$minute"
                    else
                        minute.toString()
                    ans1EvenTime.text = "$hour:$min"
                },0,0,false)
            timePickerDialog.show()
            }
        ans2EvenTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this ,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val hour = if (hourOfDay < 10)
                        "0$hourOfDay"
                    else
                        hourOfDay.toString()
                    val min = if (minute<10)
                        "0$minute"
                    else
                        minute.toString()
                    ans2EvenTime.text = "$hour:$min"
                },0,0,false)
            timePickerDialog.show()
        }
        ans3EvenTime.setOnClickListener { val timePickerDialog = TimePickerDialog(this ,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val hour = if (hourOfDay < 10)
                    "0$hourOfDay"
                else
                    hourOfDay.toString()
                val min = if (minute<10)
                    "0$minute"
                else
                    minute.toString()
                ans3EvenTime.text = "$hour:$min"
            },0,0,false)
            timePickerDialog.show() }
        submitNap.setOnClickListener { submit() }

    }
    private fun submit(){
        val records = RecordsNap(dateSetEven.text.toString(), ans1EvenTime.text.toString(), ans2EvenTime.text.toString(), ans3EvenTime.text.toString())
        mDatabaseReference.child(dateSetEven.text.toString()).child("Nap").setValue(records)
        finish()
    }
}
