package ipca.appscore.photoshaream2122_sergi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentHomeBinding
import ipca.appscore.photoshaream2122_sergi.models.Photo
import java.util.function.ToDoubleBiFunction

class HomeFragment : Fragment() {

    var photosList = arrayListOf<Photo>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var mAdapter : RecyclerView.Adapter<*>? = null
    private var mLayoutManager: LinearLayoutManager? = null



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
        mAdapter = PhotoAdapter()
        binding.recycleViewPhotos.adapter = mAdapter
        binding.recycleViewPhotos.itemAnimator = DefaultItemAnimator()



    }

   inner class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder> (){

       inner class ViewHolder ( val view: View) : RecyclerView.ViewHolder(view)

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           return ViewHolder(
               LayoutInflater.from(parent.context).inflate(R.layout.row_view_photos, parent, false)

           )

       }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           holder.view.findViewById<TextView>(R.id.textView_row_description).text = photosList[position].description


           holder.view.apply {
               val textViewDescription = findViewById<TextView>(R.id.textView_row_description)
               textViewDescription.text = photosList[position].description


               val imageViewPhoto = findViewById<ImageView>(R.id.imageView_row_Photo)
               Glide.with(this).load(photosList[position].imageUrl).into(imageViewPhoto)


           }

       }

       override fun getItemCount(): Int {
            return photosList.size
       }
   }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "HomeFragment"
    }
}