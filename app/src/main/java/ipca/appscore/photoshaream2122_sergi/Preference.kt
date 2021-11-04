package ipca.appscore.photoshaream2122_sergi

import android.content.Context
import android.content.SharedPreferences


class Preference {

    var loginPrefer : String

    get() = preferencesShared.getString("LOGIN_PREF","") as String
            set(value) {
                val editor: SharedPreferences.Editor = preferencesShared.edit()
                editor.putString("LOGIN_PREF", value)
                editor.apply()

            }


    lateinit var preferencesShared : SharedPreferences
    constructor(context: Context){
        preferencesShared = context
            .getSharedPreferences("Photo_PREFER",Context.MODE_PRIVATE)

    }
}

