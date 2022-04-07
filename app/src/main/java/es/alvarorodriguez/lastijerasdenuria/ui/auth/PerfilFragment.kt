package es.alvarorodriguez.lastijerasdenuria.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.remote.auth.AuthDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentPerfilBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.auth.AuthViewModel
import es.alvarorodriguez.lastijerasdenuria.presentation.auth.AuthViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.repositories.auth.AuthRepoImpl
import java.lang.Exception

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var binding: FragmentPerfilBinding
    private var bitmap: Bitmap? = null

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(
            AuthDataSource()
        )
    ) }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)
        profileImageClick()
        btnProfileClick()
    }

    private fun profileImageClick() {
        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startForResult.launch(takePictureIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No se encontró app para abrir la camara",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun btnProfileClick() {
        binding.btnProfile.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val alertDialog =
                AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()
            bitmap?.let { bitmap ->
                if (username.isNotEmpty()) {
                    updateProfile(bitmap, username, alertDialog)
                }
            }
        }
    }

    private fun updateProfile(
        bitmap: Bitmap,
        username: String,
        alertDialog: AlertDialog
    ) {
        viewModel.updateUserProfile(bitmap, username).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    alertDialog.show()
                }
                is Resource.Success -> {
                    alertDialog.dismiss()
                    Toast.makeText(requireContext(), "Good!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_perfilFragment_to_homeFragment)
                }
                is Resource.Failure -> {
                    alertDialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}