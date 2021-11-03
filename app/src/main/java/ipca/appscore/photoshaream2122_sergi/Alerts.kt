package ipca.appscore.photoshaream2122_sergi

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun alertNotification (context: Activity, message: String): AlertDialog? {

    val dialogView  : View = context.layoutInflater.inflate(R.layout.alert_dialog, null)

    val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(dialogView)

    val alertDialog = builder.create()
        alertDialog.window?.setGravity(Gravity.CENTER)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    val textViewTitle = dialogView.findViewById<TextView>(R.id.textView_alertMessage)
        textViewTitle.text = message

    alertDialog.show()

    GlobalScope.launch (Dispatchers.Main){
        delay(5000)
        alertDialog.dismiss()


    }

    return alertDialog
}