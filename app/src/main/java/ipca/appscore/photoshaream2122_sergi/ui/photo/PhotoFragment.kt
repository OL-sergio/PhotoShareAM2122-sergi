package ipca.appscore.photoshaream2122_sergi.ui.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentPhotoBinding
import ipca.appscore.photoshaream2122_sergi.models.Photo


class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!
    private var bitmap : Bitmap? = null


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


        }


        // Acceding android component to take a photo
        binding.fabTakePhoto.setOnClickListener {
            val takePictureIntent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            //val intent  = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST )

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


    }

}