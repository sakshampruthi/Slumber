package com.thesleepconsultingcompany.sleepdoctor

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firbaseUser: FirebaseUser
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val TAG  ="LoginActivity"
    val RC_SIGN_IN:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth =FirebaseAuth.getInstance()
        val intent = Intent(this,SignUp::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        googleSign.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)

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
            .addOnCompleteListener(this, OnCompleteListener {
                if (it.isSuccessful){
                    Log.d(TAG," SignInWithCredential: success");
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    startActivity( Intent(applicationContext,MainActivity::class.java))
                    finish()
                }
                else{
                    Log.d(TAG,"signInWithCredential: Failed",it.exception)
                    Snackbar.make( findViewById(R.id.googleSign), "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                }
            })

    }
}
