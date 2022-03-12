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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentPhotoBinding

import ipca.appscore.photoshaream2122_sergi.models.Photo
import java.io.ByteArrayOutputStream
import java.util.*


class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var bitmap : Bitmap? = null

    private val mDB = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inf
        // late the layout for this fragment
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
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
            uploadTask.continueWithTask { task ->
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
                storageRef.child("imgfeed/${Firebase.auth.currentUser?.uid}/$filename")
                    .downloadUrl.addOnSuccessListener {

                    val downloadUri = it.toString()

                    Log.d(TAG, "DocumentSnapshot added with ID: ${downloadUri}")
                    val photo = Photo(
                        binding.editTextDescriptionSend.text.toString(),
                        downloadUri
                    )

                        mDB.collection("imgfeed")
                        .add(photo.toHash())
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

                        }.addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)

                        }
                }.addOnFailureListener {
                        // Handle any errors
                }
            }
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)

                }
            }

        // Acceding android component to take a photo
        _binding!!.fabTakePhoto.setOnClickListener {

            //intent to open camera app
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)

        }

    }

    //Confirming that the request and result are true saving the photo on bitmap and imageView
    private fun handleCameraImage(intent: Intent?) {

        val bm: Bitmap? = intent?.extras?.get("data") as? Bitmap

        bm?.let {
            _binding!!.imageViewPhotoView.setImageBitmap(it)
            _binding!!.fabTakePhoto.visibility = View.VISIBLE
            bitmap = bm
        }
    }



 /*  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

    companion object{
        const val CAMERA_PIC_REQUEST = 1001
        const val TAG = "PhotoFragment"

    }

}