package com.thesleepconsultingcompany.slumber.ui.sleep

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thesleepconsultingcompany.slumber.R
import com.thesleepconsultingcompany.slumber.models.RecordsWakeUp
import com.thesleepconsultingcompany.slumber.models.SharedPreference
import kotlinx.android.synthetic.main.activity_wakeup.*
import java.util.*
import kotlin.collections.HashSet


class WakeupActivity : AppCompatActivity() {

    val context = this
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var date:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wakeup)
        val toolbar = findViewById<Toolbar>(R.id.toolbarcom)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv = findViewById<TextView>(R.id.tvtitle)
        tv.text = "Wake Up Entry"
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid).child("Records")

        ans4.setOnClickListener {

            showCustomTimePicker()
        }
        setBedSpinner()
        setSleepSpinner()
        setWakeUpReason()


        ans1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(ans1.selectedItem.toString() == "Add Activity...") {
                    val alertDialog = MaterialAlertDialogBuilder(this@WakeupActivity)
                    alertDialog.setTitle("Add Activity")
                    val input = EditText(this@WakeupActivity)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    lp.marginEnd = 10
                    lp.marginStart = 10
                    input.layoutParams = lp
                    alertDialog.setView(input)

                    alertDialog.setPositiveButton("Add"
                    ) { _, _ ->
                        val list = SharedPreference.SavedSharedPreference.getBeforeSleep(context) as Collection<String>
                        val listSleep = arrayListOf<String>()
                        listSleep.addAll(list)
                        val activity = input.text.toString()
                        listSleep.add(listSleep.size - 1 , activity)
                        val set = HashSet<String>()
                        set.addAll(listSleep)
                        SharedPreference.SavedSharedPreference.setBeforeSleep(context, set)
                        setSleepSpinner()
                    }
                    alertDialog.setNegativeButton("Cancel"){
                            dialog, _ ->
                        dialog.dismiss()
                        setSleepSpinner()
                    }
                    alertDialog.show()
                }
            }
        }

        ans2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(ans2.selectedItem.toString() == "Add Activity...") {
                    val alertDialog = MaterialAlertDialogBuilder(this@WakeupActivity)
                    alertDialog.setTitle("Add Activity")
                    val input = EditText(this@WakeupActivity)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    lp.marginEnd = 10
                    lp.marginStart = 10
                    input.layoutParams = lp
                    alertDialog.setView(input)

                    alertDialog.setPositiveButton("Add"
                    ) { _, _ ->
                        val list2 = SharedPreference.SavedSharedPreference.getBeforeBed(context) as Collection<String>
                        val listBed = arrayListOf<String>()
                        listBed.addAll(list2)
                        val activity = input.text.toString()
                        listBed.add(listBed.size - 1 , activity)
                        val set = HashSet<String>()
                        set.addAll(listBed)
                        SharedPreference.SavedSharedPreference.setBeforeBed(context, set)
                        setBedSpinner()
                    }
                    alertDialog.setNegativeButton("Cancel"){
                            dialog, _ ->
                        dialog.dismiss()
                        setBedSpinner()
                    }
                    alertDialog.show()
                }
            }
        }

        ans5.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(ans5.selectedItem.toString() == "Add Reason...") {
                    val alertDialog = MaterialAlertDialogBuilder(this@WakeupActivity)
                    alertDialog.setTitle("Add Reason")
                    val input = EditText(this@WakeupActivity)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    lp.marginEnd = 10
                    lp.marginStart = 10
                    input.layoutParams = lp
                    alertDialog.setView(input)

                    alertDialog.setPositiveButton("ADD") { _, _ ->
                        val set =
                            SharedPreference.SavedSharedPreference.getWakeupReason(context) as Collection<String>
                        val list = arrayListOf<String>()
                        list.addAll(set)
                        val reason = input.text.toString()
                        list.add(list.size - 1, reason)
                        val hash = HashSet<String>()
                        hash.addAll(list)
                        SharedPreference.SavedSharedPreference.setWakeupReason(context, hash)
                        setWakeUpReason()
                    }
                    alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                        setWakeUpReason()
                    }
                    alertDialog.show()
                }
            }
        }
        submitwakeup.setOnClickListener {
            if(ans3.progress > 0 && (ans4.text.toString().equals("00:00") or ans4.text.toString().equals("0:0")))
                Toast.makeText(this,"PLease enter the duration of wake state",Toast.LENGTH_SHORT).show()
            else
                submit()
        }

        val myCalendar = Calendar.getInstance()
        val year = myCalendar[Calendar.YEAR]
        var month = myCalendar[Calendar.MONTH]
        val day = myCalendar[Calendar.DAY_OF_MONTH]
        month += 1
        var mon = if (month < 10) "0$month" else month.toString()
        val d = if(day < 10) "0$day" else day.toString()
        dateset.text = "$d-$mon-$year"
        dateset.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this,
                    OnDateSetListener { view, year, month, dayOfMonth ->
                        var month = month
                        month = month + 1
                        val day: String
                        var mon = if (month < 10) "0$month" else month.toString()
                        day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                        date = "$day-$mon-$year"
                        dateset.text = date
                    }, year, month, day
                )
            datePickerDialog.show()

        }
    }
    private fun submit(){

        val records = RecordsWakeUp(
            dateset.text as String?,
            ans1.selectedItem.toString(),
            ans2.selectedItem.toString(),
            ans3.progress.toString(),
            ans4.text.toString(),
            ans5.selectedItem.toString()
        )
        mDatabaseReference.child(dateset.text.toString()).child("After Wake Up").setValue(records)
        finish()
    }
    private fun setSleepSpinner(){
        val list = SharedPreference.SavedSharedPreference.getBeforeSleep(context) as Collection<String>
        val listSleep = arrayListOf<String>()
        listSleep.addAll(list)
        val adapterSleep = ArrayAdapter(this,android.R.layout.simple_spinner_item,listSleep)
        adapterSleep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ans1.adapter = adapterSleep

    }
    private fun setBedSpinner(){
        val list2 = SharedPreference.SavedSharedPreference.getBeforeBed(context) as Collection<String>
        val listBed = arrayListOf<String>()
        listBed.addAll(list2)
        val adapterBed = ArrayAdapter(this,android.R.layout.simple_spinner_item,listBed)
        adapterBed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ans2.adapter = adapterBed
    }
    private fun setWakeUpReason(){
        val list = SharedPreference.SavedSharedPreference.getWakeupReason(context) as Collection<String>
        val listWake = arrayListOf<String>()
        listWake.addAll(list)
        val adapterWake = ArrayAdapter(this, android.R.layout.simple_spinner_item, listWake)
        adapterWake.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ans5.adapter = adapterWake
    }

    private fun showCustomTimePicker(){
        val timePickerDialog = TimePickerDialog(context,OnTimeSetListener { view, hourOfDay, minute ->
            ans4.text = ""+hourOfDay+":"+ minute
        },0,0,true)
        timePickerDialog.show()
    }
}
