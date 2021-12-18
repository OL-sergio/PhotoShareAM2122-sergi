package ipca.appscore.photoshaream2122_sergi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ipca.appscore.photoshaream2122_sergi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    private var notificationReceiver : NotificationReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navView: BottomNavigationView = _binding.navView

        val navController = findNavController( R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_photo
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

                intent?.extras?.getString(NotificationService.NOTIFICATION_MESSAGE)?.let {
                            alertNotification(this@MainActivity, it)

                    }
        }

    }

    override fun onResume() {
        super.onResume()
        notificationReceiver = NotificationReceiver()
            this.registerReceiver(notificationReceiver, IntentFilter(NotificationService.BROADCAST_NEW_NOTIFICATION))
    }

    override fun onPause() {
        super.onPause()
            notificationReceiver?.let {
                this.unregisterReceiver(it)
            }
    }

}

