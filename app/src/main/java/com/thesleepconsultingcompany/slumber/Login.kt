package com.thesleepconsultingcompany.slumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.thesleepconsultingcompany.slumber.models.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    var firebaseUser: FirebaseUser? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val TAG  ="LoginActivity"
    val RC_SIGN_IN:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth =FirebaseAuth.getInstance()

        val intent = Intent(this, SignUp::class.java)
        email.editText?.doAfterTextChanged {
            email.error=null
        }
        password.editText?.doAfterTextChanged {
            password.error = null
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        signup.setOnClickListener {
            startActivity(Intent(this,
                SignUp::class.java))
        }
        forgotpassword.setOnClickListener {
            val Stremail = email.editText!!.text.toString()
            if (Stremail.isEmpty()) email.error =
                "Enter your Email address" else FirebaseAuth.getInstance()
                .sendPasswordResetEmail(Stremail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) Toast.makeText(
                        applicationContext,
                        "Password Reset Email sent. Check Inbox",
                        Toast.LENGTH_LONG
                    ).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Email not registered!",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        googleSign.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)

        }
        login.setOnClickListener {
           loggedIn()
        }

    }

    private fun loggedIn() {
        try {
            val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }catch (e: Exception){
            Log.d(TAG,"Keyboard not hidden",e)
        }
        login.isEnabled = false
        signup.isEnabled = false
        MainProgressBar.visibility = View.VISIBLE

        val stremail = email.editText?.text.toString()
        val strpassword = password.editText?.text.toString()

        if(stremail.isEmpty() || strpassword.isEmpty()){
            if(stremail.isEmpty())
                email.error="Field cannot be empty"
            if(strpassword.isEmpty())
                password.error = "Field cannot be empty"

            login.isEnabled = true
            MainProgressBar.visibility = View.GONE
            signup.isEnabled = true
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(stremail,strpassword).addOnCompleteListener {
                if(!it.isSuccessful){
                    firebaseAuth.fetchSignInMethodsForEmail(stremail)
                        .addOnCompleteListener {
                            val correct_password = !it.result.signInMethods?.isEmpty()!!
                            if(correct_password)
                                Snackbar.make(findViewById(R.id.loginlayout),"Incorrect Password",Snackbar.LENGTH_LONG).show()
                            else
                                Snackbar.make(findViewById(R.id.loginlayout),"Email not registered",Snackbar.LENGTH_LONG).show()
                            MainProgressBar.visibility =  View.GONE
                            login.isEnabled = true
                            signup.isEnabled = true
                        }
                }
                else{
                    if(firebaseAuth.currentUser?.isEmailVerified!!){
                        SharedPreference.SavedSharedPreference.setEmail(stremail,applicationContext)
                        val intent =Intent(this,
                            MainActivity::class.java)
                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                    }
                    else{
                        Snackbar.make(findViewById(R.id.loginlayout),"Email Not Verified! Please verify before continuing!",Snackbar.LENGTH_LONG).show()
                        MainProgressBar.visibility = View.GONE
                        login.isEnabled = true
                        signup.isEnabled = true
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account:GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e:ApiException){
                Log.d(TAG,"Google Sign in Failed", e)
                Toast.makeText(this,"Google Sign In Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.id)
        val credential:AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    val newUser = it.result.additionalUserInfo?.isNewUser as Boolean
                    if (newUser){
                        startActivity(Intent(this,
                            DetailsActivity::class.java))
                        finish()
                    }
                    else {
                        Log.d(TAG, " SignInWithCredential: success");
                        firebaseUser = firebaseAuth.currentUser!!
                        firebaseUser!!.email?.let { it1 -> SharedPreference.SavedSharedPreference.setEmail(it1,applicationContext) }
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                } else{
                    Log.d(TAG,"signInWithCredential: Failed",it.exception)
                    Snackbar.make( findViewById(R.id.googleSign), "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }
            }

    }


    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            Log.d(TAG,"Login with google")
            startActivity(Intent(this,
                MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }

        else if(firebaseUser!=null){
            Log.d(TAG,"Login with firebase")
            startActivity(Intent(this,
                MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
        else if(SharedPreference.SavedSharedPreference.getEmail(applicationContext)?.isNotEmpty()!!){
            Log.d(TAG,"Login with sharepref")
            startActivity(Intent(this,
                MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }
}

