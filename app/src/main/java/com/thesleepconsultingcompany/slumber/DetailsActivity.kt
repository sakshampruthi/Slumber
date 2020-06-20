package com.thesleepconsultingcompany.slumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.thesleepconsultingcompany.slumber.models.SharedPreference
import com.thesleepconsultingcompany.slumber.models.User
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NAME = "name"
        val EXTRA_EMAIL = "email"
        val EXTRA_PASSWORD = "password"
        val EXTRA_PREV = "previous"
    }

    private val TAG = "Step Two Sign Up"
    lateinit var mAuth: FirebaseAuth
    var receivedPRev: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        mAuth = FirebaseAuth.getInstance()

        val intent = intent
        val receivedName =
            intent.getStringExtra(EXTRA_NAME)
        val receivedEmail =
            intent.getStringExtra(EXTRA_EMAIL)
        val receivedPassword =
            intent.getStringExtra(EXTRA_PASSWORD)
        receivedPRev = intent.getStringExtra(EXTRA_PREV)

        if (receivedPRev == null) {
            User.addUser(
                mAuth.currentUser!!.email,
                mAuth.currentUser!!.displayName,
                null,
                "10",
                "other"
            )
        }
        stepTwoButton.setOnClickListener {
            val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            val age: String = textAge.editText?.text.toString()
            val sex: String = sexSpinner.selectedItem.toString()
            if(age.isEmpty() or sex.equals("Select")) {
                if (age.isEmpty())
                    textpass.error = "Enter a Valid age"
                if (sex.equals("Select")) {
                    val tv = sexSpinner.selectedView as TextView
                    tv.error = "Select an option"
                }
            }
            else{
                if(receivedPRev == null){  //google
                    User.addUser(mAuth.currentUser?.email,mAuth.currentUser?.displayName,null,age, sex)
                    mAuth.currentUser?.email?.let { it1 ->
                        SharedPreference.SavedSharedPreference.setEmail(
                            it1,applicationContext)
                    }
                    startActivity(Intent(this,
                        MainActivity::class.java))
                    finish()
                }
                else{
                    mAuth.createUserWithEmailAndPassword(receivedEmail, receivedPassword)
                                .addOnCompleteListener { task ->
                                    if(task.isSuccessful){
                                        User.addUser(receivedEmail,receivedName,receivedPassword,age,sex)
                                        mAuth.currentUser?.sendEmailVerification()
                                            ?.addOnCompleteListener{ task ->
                                                if(task.isSuccessful)
                                                    Toast.makeText(this,"Registered! Email Verification sent",Toast.LENGTH_LONG).show()
                                                else
                                                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                                            }

                                        Log.d(TAG,"SignUp Successful")
                                        startActivity(Intent(this,
                                            Login::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                        finish()
                                    }
                                }

                }
            }
        }
        textAge.editText?.doAfterTextChanged {
            textAge.error = null
        }
//        sexSpinner.setOnItemClickListener { parent, view, position, id ->
//            val temp = sexSpinner.selectedView as TextView
//            temp.error = null
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (receivedPRev != null) super.onBackPressed() else Toast.makeText(
            this,
            "Please fill in the details and click submit!",
            Toast.LENGTH_SHORT
        ).show()
    }
}
