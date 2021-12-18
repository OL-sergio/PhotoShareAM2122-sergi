package ipca.appscore.photoshaream2122_sergi

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
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
                finish()
            }

        }else{
            GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                finish()
            }

        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful){
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            Log.d(TAG, "O FCM $token")
            Toast.makeText(baseContext,"O FCM $token", Toast.LENGTH_SHORT ).show()

        } )

    }
    companion object{
        const val TAG = "SplashActivity"
    }

}