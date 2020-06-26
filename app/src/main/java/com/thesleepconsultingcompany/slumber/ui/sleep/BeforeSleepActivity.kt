package com.thesleepconsultingcompany.slumber.ui.sleep

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.hsalf.smileyrating.SmileyRating
import com.thesleepconsultingcompany.slumber.R
import com.thesleepconsultingcompany.slumber.models.RecordsBeforeSleep
import com.thesleepconsultingcompany.slumber.models.SharedPreference
import kotlinx.android.synthetic.main.activity_before_sleep.*
import kotlinx.android.synthetic.main.activity_wakeup.*
import java.util.*

class BeforeSleepActivity : AppCompatActivity() {

    lateinit var mDatabaseReference: DatabaseReference
    val context = this
    lateinit var date:String
    var time: String? = null
    var time2: String? = null
    lateinit var mood:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_sleep)

        val toolbar = findViewById<Toolbar>(R.id.toolbarcom)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv = findViewById<TextView>(R.id.tvtitle)
        tv.text = "Before Sleep Entry"

        setExerciseSpinner()

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid).child("Records")

        val myCalendar = Calendar.getInstance()
        val year = myCalendar[Calendar.YEAR]
        var month = myCalendar[Calendar.MONTH]
        val day = myCalendar[Calendar.DAY_OF_MONTH]
        month += 1
        var mon = if (month < 10) "0$month" else month.toString()
        val d = if(day < 10) "0$day" else day.toString()
        datesetbefore.text = "$d-$mon-$year"
        datesetbefore.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var month = month
                        month = month + 1
                        val day: String
                        var mon = if (month < 10) "0$month" else month.toString()
                        day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                        date = "$day-$mon-$year"
                        datesetbefore.text = date
                    }, year, month, day
                )
            datePickerDialog.show()

        }

        ans1before.setTitle(SmileyRating.Type.GREAT,"Energetic")
        ans1before.setTitle(SmileyRating.Type.TERRIBLE,"Tired")
        ans1before.setTitle(SmileyRating.Type.BAD,"Sleepy")
        ans1before.setTitle(SmileyRating.Type.OKAY,"Neutral")
        ans1before.setRating(SmileyRating.Type.OKAY,true)
        ans1before.setRating(3,true)

        ans1before.setSmileySelectedListener { type ->
            when(type.rating){
                1 -> mood = "Tired"
                2 -> mood = "Sleepy"
                3 -> mood = "Neutral"
                4 -> mood = "Good"
                5 -> mood = "Energetic"
            }
        }
        subtract.setOnClickListener {
            if (measure.text.toString().toInt() > 0) {
                val m: Int = measure.text.toString().toInt() - 1
                measure.text = m.toString()
            }
        }
        add.setOnClickListener {
            val m = measure.text.toString().toInt() + 1
            measure.text = m.toString()
        }
        ans3beforetext.setOnClickListener {
            time = showCustomTimePicker()
        }
        ans4beforeTime.setOnClickListener {
            time2 = showCustomTimePicker()
        }
        ans4beforeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(ans4beforeSpinner.selectedItem == "Add Exercise..."){
                    val alertDialog = MaterialAlertDialogBuilder(this@BeforeSleepActivity)
                    alertDialog.setTitle("Add Exercise")
                    val input = EditText(this@BeforeSleepActivity)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    val lp = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                    )
                    lp.marginEnd = 10
                    lp.marginStart = 10
                    input.layoutParams = lp
                    alertDialog.setView(input)

                    alertDialog.setPositiveButton("Add"){
                        dialog, which ->
                        val list = SharedPreference.SavedSharedPreference.getExercise(context) as Collection<String>
                        val listExercise = arrayListOf<String>()
                        listExercise.addAll(list)
                        val activity = input.text.toString()
                        listExercise.add(listExercise.size - 1 , activity)
                        val set = HashSet<String>()
                        set.addAll(listExercise)
                        SharedPreference.SavedSharedPreference.setExcercise(context, set)
                        setExerciseSpinner()
                    }
                    alertDialog.show()
                }
            }

        }
        ans5before.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(ans5before.selectedItem.toString() == "Add Activity..."){
                    val alertDialog = MaterialAlertDialogBuilder(this@BeforeSleepActivity)
                    alertDialog.setTitle("Add Activity")
                    val input = EditText(this@BeforeSleepActivity)
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
        submitBefore.setOnClickListener {
            submit()
        }

    }
    private  fun submit(){
        val records = RecordsBeforeSleep(datesetbefore.text.toString(), mood, measure.text.toString(), ans3beforetext.text.toString(), ans4beforeSpinner.selectedItem.toString(), ans4beforeTime.text.toString(), ans5before.selectedItem.toString())
        mDatabaseReference.child(dateset.text.toString()).child("Before Going to Sleep").setValue(records)
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
    private fun setExerciseSpinner(){
        val list = SharedPreference.SavedSharedPreference.getExercise(context) as Collection<String>
        val listExercise = arrayListOf<String>()
        listExercise.addAll(list)
        val adapterExercise = ArrayAdapter(this,android.R.layout.simple_spinner_item,listExercise)
        adapterExercise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ans4beforeSpinner.adapter = adapterExercise
    }
    private fun showCustomTimePicker(): String? {
        var time: String? =null
        val timePickerDialog = TimePickerDialog(context ,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                time = "" + hourOfDay + ":" + minute
            },0,0,true)
        timePickerDialog.show()
        return time
    }
}
