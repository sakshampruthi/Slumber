package com.thesleepconsultingcompany.slumber.ui.sleep

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thesleepconsultingcompany.slumber.R
import kotlinx.android.synthetic.main.activity_wakeup.*


class WakeupActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wakeup)

        ans4.setOnClickListener {
            showCustomTimePicker()
        }

    }

    private fun showCustomTimePicker(){
        val timePickerDialog = TimePickerDialog(context,OnTimeSetListener { view, hourOfDay, minute ->
            ans4.text = ""+hourOfDay+":"+ minute
        },0,0,true)
        timePickerDialog.show()
    }
}
