package ipca.appscore.photoshaream2122_sergi

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var _binder: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binder = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binder.root)

        auth = Firebase.auth

        supportActionBar?.hide()
        _binder.textViewContent.text = "Photo Share"

        val currentUser = auth.currentUser

        if (currentUser != null){
            GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
            }

        }else{
            GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
            }

            //After 1 min returns to context activity
            GlobalScope.launch(Dispatchers.Main) {
                delay(60000)
                val intent = Intent(baseContext, SplashActivity::class.java)
                startActivity(intent)
            }
        }

    }

}