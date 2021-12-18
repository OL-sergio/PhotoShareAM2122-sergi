package ipca.appscore.photoshaream2122_sergi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.appscore.photoshaream2122_sergi.databinding.ActivityLoginBinding
import ipca.appscore.photoshaream2122_sergi.databinding.ActivitySplashBinding
import ipca.appscore.photoshaream2122_sergi.models.User
import ipca.appscore.photoshaream2122_sergi.ui.photo.PhotoFragment


class LoginActivity : AppCompatActivity() {

    private lateinit var _binder: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _binder = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        // Initialize Firebase Auth
        auth = Firebase.auth


        _binder.textViewRealizeRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        _binder.editTextLoginEmail.setText(Preference(this).loginPrefer)

        _binder.buttonLogin.setOnClickListener {
            val email: String = _binder.editTextLoginEmail.text.toString()
            val password: String = _binder.editTextLoginPassword.text.toString()

            if (email == "") {

                Toast.makeText(this@LoginActivity, "Please write email.", Toast.LENGTH_SHORT)
                    .show()
            } else if (password == "") {
                Toast.makeText(this@LoginActivity, "Please write password.", Toast.LENGTH_SHORT)
                    .show()

            } else {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Preference(this).loginPrefer = email
                            val user = User("", "", email)
                            val db = Firebase.firestore

                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                            db.collection("user")
                                .add(user.toHash())
                                .addOnSuccessListener { documentReference ->
                                    Log.d(
                                        PhotoFragment.TAG,
                                        "DocumentSnapshot added with ID:${documentReference.id}"
                                    )

                                }.addOnFailureListener { e ->

                                    Log.w(PhotoFragment.TAG, "Error Adding Document", e)
                                }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Falha a realizar login",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

            }

        }
    }
}