package com.thesleepconsultingcompany.slumber.ui.sleep

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thesleepconsultingcompany.slumber.R
import kotlinx.android.synthetic.main.activity_total_hours.*
import java.util.*
import kotlin.collections.HashMap


class TotalHoursActivity : AppCompatActivity() {

    lateinit var date:String

    lateinit var mDatabaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_hours)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid).child("Records")


        val myCalendar = Calendar.getInstance()
        val year = myCalendar[Calendar.YEAR]
        var month = myCalendar[Calendar.MONTH]
        val day = myCalendar[Calendar.DAY_OF_MONTH]
        month += 1
        var mon = if (month < 10) "0$month" else month.toString()
        val d = if(day < 10) "0$day" else day.toString()
        dateHours.text = "$d-$mon-$year"
        dateHours.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var month = month
                        month = month + 1
                        val day: String
                        var mon = if (month < 10) "0$month" else month.toString()
                        day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                        date = "$day-$mon-$year"
                        dateHours.text = date
                    }, year, month, day
                )
            datePickerDialog.show()
        }
        croller.setOnProgressChangedListener {
            val set = HashMap<String,String>()
            set.put("Total Sleep ","$it hours")
            mDatabaseReference.child(dateHours.text.toString()).setValue(set)
        }
    }


}