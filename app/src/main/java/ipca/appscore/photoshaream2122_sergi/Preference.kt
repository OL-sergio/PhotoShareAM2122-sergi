package ipca.appscore.photoshaream2122_sergi

import android.content.Context
import android.content.SharedPreferences


class Preference(context: Context) {

    var loginPrefer : String

    get() = preferencesShared.getString("LOGIN_PREF","") as String
            set(value) {
                val editor: SharedPreferences.Editor = preferencesShared.edit()
                editor.putString("LOGIN_PREF", value)
                editor.apply()
            }

    private var preferencesShared : SharedPreferences = context
        .getSharedPreferences("Photo_PREFER",Context.MODE_PRIVATE)

}

