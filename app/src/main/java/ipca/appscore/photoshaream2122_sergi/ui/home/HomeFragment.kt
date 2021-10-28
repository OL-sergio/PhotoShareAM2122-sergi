package ipca.appscore.photoshaream2122_sergi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentHomeBinding

import ipca.appscore.photoshaream2122_sergi.models.Photo


class HomeFragment : Fragment() {

    var photos = arrayListOf<Photo>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var mAdapter : RecyclerView.Adapter<*>? = null
    private var mLayoutManager: LinearLayoutManager? = null

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycleViewPhotos.layoutManager = mLayoutManager
        mAdapter = PhotoAdapter()
        binding.recycleViewPhotos.adapter = mAdapter

        db.collection("imgfeed")
            .addSnapshotListener{ documents, _ ->
                documents?.let {
                    photos.clear()
                       for (document in it){
                           Log.d(TAG, "${document.id} => ${document.data}")
                           val photo = Photo.fromHash(document)
                           photos.add(photo)


                    }
                    mAdapter?.notifyDataSetChanged()
                }
            }

       setHasOptionsMenu(true)
        }

  /*  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }  */



    inner class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>(){

       inner class ViewHolder ( val view: View) : RecyclerView.ViewHolder(view)

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           return ViewHolder(
               LayoutInflater.from(parent.context).inflate(R.layout.row_view_photos, parent, false)
           )
       }


       override fun onBindViewHolder(holder: ViewHolder, position: Int) {

          // holder.view.findViewById<TextView>(R.id.textView_row_description).text = photos[position].description

           holder.view.apply {

               val textViewDescription = findViewById<TextView>(R.id.textView_row_description)
               textViewDescription.text = photos[position].description


               val imageViewPhoto = findViewById<ImageView>(R.id.imageView_row_photoView)
               Glide.with(this).load(photos[position].imageUrl).into(imageViewPhoto)


           }

       }

       override fun getItemCount(): Int {
            return photos.size
       }
   }


    companion object{
        const val TAG = "HomeFragment"

    }
}