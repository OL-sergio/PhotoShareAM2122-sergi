package ipca.appscore.photoshaream2122_sergi

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.appscore.photoshaream2122_sergi.databinding.ActivityLoginBinding
import ipca.appscore.photoshaream2122_sergi.databinding.ActivitySplashBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var _binder: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _binder = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        _binder.buttonLogin.setOnClickListener {
            val email : String = _binder.editTextLoginEmail.text.toString()
            val password: String = _binder.editTextLoginPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Falha a realizar login",
                            Toast.LENGTH_SHORT).show()

                    }
                }

        }

    }
}