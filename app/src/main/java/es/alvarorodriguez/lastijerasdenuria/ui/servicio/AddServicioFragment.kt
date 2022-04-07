package es.alvarorodriguez.lastijerasdenuria.ui.servicio

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.data.remote.servicio.ServicioDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentAddServicioBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServiceViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServicioViewModel
import es.alvarorodriguez.lastijerasdenuria.repositories.servicio.ServicioRepoImpl

class AddServicioFragment : Fragment(R.layout.fragment_add_servicio) {

    private lateinit var binding: FragmentAddServicioBinding

    private val viewModel by viewModels<ServicioViewModel> {
        ServiceViewModelFactory(
            ServicioRepoImpl(
                ServicioDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddServicioBinding.bind(view)
        addServiceClick()
    }

    private fun addServiceClick() {
        binding.addService.setOnClickListener {

            val name = binding.editTextName.text.toString().trim()
            val price = binding.editTextPrecio.text.toString().trim()

            val service = Servicio(name = name, price = price)

            addService(service)
        }
    }

    private fun addService(service: Servicio) {
        viewModel.addService(service).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("DATA", "onViewCreated: ${result.data}")
                    Toast.makeText(requireContext(), "Add Service", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addServicioFragment_to_servicioFragment)
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