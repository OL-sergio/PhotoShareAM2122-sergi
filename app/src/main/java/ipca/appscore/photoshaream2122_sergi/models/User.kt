package ipca.appscore.photoshaream2122_sergi.models

import android.graphics.Bitmap
import com.google.firebase.firestore.QueryDocumentSnapshot

class User {

    var name        : String = ""
    var photoUrl    : String = ""
    var email       : String = ""
    var bitmap      : Bitmap? = null
    var id          : String? = null

    constructor(name: String, photoUrl: String, email: String) {
        this.name           = name
        this.photoUrl       = photoUrl
        this.email          = email
    }


    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
        hashMap.put("name"          , name)
        hashMap.put("photoUrl"      , photoUrl)
        hashMap.put("email"         , email)
        return hashMap
    }


    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): User {
            val user = User(
                hashMap["name"] as String,
                hashMap["photoUrl"] as String ,
                hashMap["email"] as String
            )
                return user
        }
    }
}