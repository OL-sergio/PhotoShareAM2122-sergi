package ipca.appscore.photoshaream2122_sergi.ui.photo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import ipca.appscore.photoshaream2122_sergi.R
import ipca.appscore.photoshaream2122_sergi.databinding.ActivityMainBinding
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentHomeBinding
import ipca.appscore.photoshaream2122_sergi.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null

        private val binding get() = _binding!!

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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}