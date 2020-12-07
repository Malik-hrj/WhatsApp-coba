package com.example.whatsappcoba.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.whatsappcoba.MainActivity
import com.example.whatsappcoba.R
import com.example.whatsappcoba.util.DATA_USER
import com.example.whatsappcoba.util.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {


    private val firebaseDb = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setTextChangeListener(edt_email, til_email)
        setTextChangeListener(edt_password, til_password)
        setTextChangeListener(edt_name, til_name)
        setTextChangeListener(edt_phone, til_phone)
        setTextChangeListener(edt_phone, til_phone)
        progress_layout.setOnTouchListener { v, event -> true }

        btn_signup.setOnClickListener {
            onSignUp()
        }

        txt_login.setOnClickListener {
            onLogin()
        }

    }

    private fun onLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun onSignUp() {
        var procced = true

        if (edt_name.text.isNullOrEmpty()) {
            til_name.error = "Required Name"
            til_name.isErrorEnabled = true
            procced = false
        }

        if (edt_phone.text.isNullOrEmpty()) {
            til_phone.error = "Required Phone Number"
            til_phone.isErrorEnabled = true
            procced = false
        }

        if (edt_email.text.isNullOrEmpty()) {
            til_email.error = "Required Email"
            til_email.isErrorEnabled = true
            procced = false
        }

        if (edt_password.text.isNullOrEmpty()) {
            til_password.error = "Required Password"
            til_password.isErrorEnabled = true
            procced = false
        }

        if (procced) {
            progress_layout.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(
                edt_email.text.toString(),
                edt_password.text.toString()
            ).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    progress_layout.visibility = View.GONE
                    Toast.makeText(
                        this@SignupActivity,
                        "sign Up Error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT
                    ).show()
                } else if (firebaseAuth.uid != null) {
                    val email = edt_email.text.toString()
                    val phone =  edt_phone.text.toString()
                    val name = edt_name.text.toString()
                    val user = User(
                        email,
                        phone,
                        name,
                        "",
                        "Hello i'm new",
                        "",
                        ""
                    )
                    firebaseDb.collection(DATA_USER).document(firebaseAuth.uid!!).set(user)
                }
                progress_layout.visibility = View.GONE
            }.addOnFailureListener {
                progress_layout.visibility = View.GONE
                it.printStackTrace()
            }
        }
    }

    private fun setTextChangeListener(edit: EditText, til: TextInputLayout) {
        edit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }


}