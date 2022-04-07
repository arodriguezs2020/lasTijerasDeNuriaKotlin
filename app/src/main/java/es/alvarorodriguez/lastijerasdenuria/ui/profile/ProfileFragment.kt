package es.alvarorodriguez.lastijerasdenuria.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        val user = FirebaseAuth.getInstance().currentUser
        context?.let { Glide.with(it).load(user?.photoUrl.toString()).centerCrop().into(binding.imageProfile) }
        binding.txtUsername.text = user?.displayName
        binding.txtEmail.text = user?.email
    }
}