package ipca.appscore.photoshaream2122_sergi.ui.photo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentPhotoBinding
import ipca.appscore.photoshaream2122_sergi.models.Photo
import ipca.appscore.photoshaream2122_sergi.ui.home.HomeFragment.Companion.TAG
import java.io.ByteArrayOutputStream
import java.util.*

class PhotoFragment : Fragment() {



    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private var bitmap : Bitmap? = null

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inf
        // late the layout for this fragment
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)

        return _binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.fbSendPhoto.setOnClickListener {

            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val storage = Firebase.storage
            val storageRef = storage.reference
            val filename = "${UUID.randomUUID()}.jpg"
            val createImageRef =
                storageRef.child("imgfeed/${Firebase.auth.currentUser?.uid}/$filename")


            val uploadTask = createImageRef.putBytes(data)
            uploadTask.continueWith { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw  it
                    }

                }
                createImageRef.downloadUrl
            }
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads

            }.addOnSuccessListener { task ->

                val downloadUri = task.uploadSessionUri?.toString() ?: ""

                Log.d(TAG, "DocumentSnapshot added with ID: ${uploadTask.result.toString()}")
                val photo = Photo(
                    binding.editTextDescriptionSend.text.toString(),
                    downloadUri
                )
                db.collection("imgfeed")
                    .add(photo.toHash())
                    .addOnSuccessListener { referenceDocument ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${referenceDocument.id}")

                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)

                    }
            }
        }

        // Acceding android component to take a photo
        binding.fabTakePhoto.setOnClickListener {
            val takePictureIntent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            //val intent  = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST)

        }

    }

    //Confirming that the request and result are true saving the photo on bitmap and imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                CAMERA_PIC_REQUEST -> {
                    val bm : Bitmap? = data?.extras?.get("data") as? Bitmap
                    bm?.let {
                        _binding!!.imageViewPhotoView.setImageBitmap(it)
                        bitmap = bm

                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val CAMERA_PIC_REQUEST = 1001
        const val TAG = "PhotoFragment"

    }

}