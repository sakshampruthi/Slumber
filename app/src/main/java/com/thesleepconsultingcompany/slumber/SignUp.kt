package com.thesleepconsultingcompany.slumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    val TAG = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        textemail.editText?.doAfterTextChanged {
            textemail.error = null
        }
        textname.editText?.doAfterTextChanged {
            textname.error = null
        }
        textpass.editText?.doAfterTextChanged {
            textpass.error = null
        }

        signupbutton.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        try {
            val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            Log.d(TAG, "Keyboard not hidden")
        }

        val Strpassword: String = textpass.editText?.text.toString()
        val Strname: String = textname.editText?.text.toString()
        val Stremail: String = textemail.editText?.text.toString()
        Log.d(TAG, "working")


        if (Strpassword.isEmpty() or (Strpassword.length < 9) or Stremail.isEmpty() or Strname.isEmpty()) {
            if (Stremail.isEmpty())
                textemail.error = "Enter a valid email"
            if (!isEmailValid(Stremail))
                textemail.error = "Email entered is invalid"
            if (Strname.isEmpty())
                textname.error = "Enter full name"
            if (Strpassword.isEmpty())
                textpass.error = "Enter password"
            if (Strpassword.length < 9)
                textpass.error = "Password should contain at least 8 characters"

        } else {
            mAuth = FirebaseAuth.getInstance()
            Log.d(TAG, "working")
            mAuth.fetchSignInMethodsForEmail(Stremail)
                .addOnCompleteListener { task ->
                    val check = !task.result.signInMethods?.isEmpty()!!
                    if (check)
                        Toast.makeText(this, "Email already registered", Toast.LENGTH_LONG).show()
                    else {
                        val intent = Intent(this, DetailsActivity::class.java)
                        intent.putExtra(DetailsActivity.EXTRA_NAME, Strname)
                        intent.putExtra(DetailsActivity.EXTRA_EMAIL, Stremail)
                        intent.putExtra(DetailsActivity.EXTRA_PASSWORD, Strpassword)
                        intent.putExtra(DetailsActivity.EXTRA_PREV, "SignUp")
                        startActivity(intent)
                        finish()

                    }

                }

        }
    }
        fun isEmailValid(email: String?): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern =
                Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

}
