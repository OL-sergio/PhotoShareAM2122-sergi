package ipca.appscore.photoshaream2122_sergi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ipca.appscore.photoshaream2122_sergi.databinding.ActivityRegisterBinding
import java.util.*
import kotlin.collections.HashMap


class RegisterActivity : AppCompatActivity() {

    private lateinit var _binder : ActivityRegisterBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference
    private var firebaseUserID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binder = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        auth = FirebaseAuth.getInstance()

        _binder.buttonSaveRegister.setOnClickListener {

            registerUser()

        }
    }

    private fun registerUser() {

        val username    : String = _binder.editTextTextUserName.text.toString()
        val email       : String = _binder.editTextTextEmailAddress.text.toString()
        val password    : String = _binder.editTextTextPassword.text.toString()

        if (username == "") {

            Toast.makeText(this@RegisterActivity, "Please write username.", Toast.LENGTH_SHORT)
                .show()
        } else if (email == "") {

            Toast.makeText(this@RegisterActivity, "Please write email.", Toast.LENGTH_SHORT).show()
        } else if (password == "") {

            Toast.makeText(this@RegisterActivity, "Please write password.", Toast.LENGTH_SHORT)
                .show()

        } else {
            auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        firebaseUserID = auth.currentUser!!.uid
                        refUsers = Firebase.database.reference.child("Users").child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username
                        userHashMap["profile"] =
                            "https://firebasestorage.googleapis.com/v0/b/messegerapp-d957e.appspot.com/o/profile-icons-user.jpeg?alt=media&token=428006b0-d8c8-4295-aa67-4a955146b2c5"
                        userHashMap["cover"] =
                            "https://firebasestorage.googleapis.com/v0/b/messegerapp-d957e.appspot.com/o/profile-imag.jfif?alt=media&token=f7a52a32-8169-4061-b0f5-d3f4552fb5cc"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = username.lowercase(Locale.getDefault())
                        userHashMap["facebook"] = "https://m.facebook.com"
                        userHashMap["instagram"] = "https://m.instagram.com"
                        userHashMap["website"] = "https://www.google.com"
                        refUsers.updateChildren(userHashMap)


                            .addOnCompleteListener {
                                if (task.isSuccessful) {

                                    val intent =
                                        Intent(this@RegisterActivity, LoginActivity::class.java)
                                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity, "Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()

                                }
                            }
                    }
                }
        }
    }
}
