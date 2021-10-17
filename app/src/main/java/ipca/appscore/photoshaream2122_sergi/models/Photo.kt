package ipca.appscore.photoshaream2122_sergi.models

import android.graphics.Bitmap
import android.media.Image
import com.google.firebase.firestore.QueryDocumentSnapshot

class Photo  {
    var description : String = ""
    var imageUrl    : String = ""
    var bitmap      : Bitmap? = null
    var id          : String? = null

    constructor(description: String, imageUrl: String) {
        this.description    = description
        this.imageUrl       = imageUrl
    }

    fun toHash() : HashMap<String, Any>{
        val hashMap = HashMap<String,Any>()
            hashMap.put("description"   , description)
            hashMap.put("imageUrl"      , imageUrl)
        return hashMap
    }


    companion object {
        fun fromHash( hashMap: QueryDocumentSnapshot): Photo {
            val photoList = Photo(
                hashMap["description"] as String,
                hashMap["imageUrl"] as String
            )
        return photoList
        }
    }

}