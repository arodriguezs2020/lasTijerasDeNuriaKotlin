package es.alvarorodriguez.lastijerasdenuria.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.remote.auth.AuthDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentRegisterBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.auth.AuthViewModel
import es.alvarorodriguez.lastijerasdenuria.presentation.auth.AuthViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.repositories.auth.AuthRepoImpl

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        doRegister()
    }

    private fun doRegister() {
        binding.btnSignup.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val repeatPassword = binding.editTextRepeatPassword.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()
            validateCredentials(name, email, password, repeatPassword, phone)
            signUp(name, email, password, phone)
        }
    }

    private fun validateCredentials(name: String, email: String, password: String, repeatPassword: String, phone: String) {
        if (name.isEmpty()) {
            binding.editTextName.error = "Name is empty"
            return
        }
        
        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "E-mail isn't correct"
            return
        }
        
        if (password != repeatPassword) {
            binding.editTextRepeatPassword.error = "Not equals"
            return
        }
        
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return
        }
        
        if (repeatPassword.isEmpty()) {
            binding.editTextRepeatPassword.error = "Password is empty"
            return
        }
        
        if (phone.isEmpty()) {
            binding.editTextPhone.error = "Phone is empty"
            return
        }
        
        if (phone.length < 9) {
            binding.editTextPhone.error = "Nine characters min"
            return
        }
    }

    private fun signUp(name: String, email: String, password: String, phone: String) {
        viewModel.signUp(name, email, password, phone).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_perfilFragment)
                    Toast.makeText(requireContext(), "Register successful", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
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