package es.alvarorodriguez.lastijerasdenuria.ui.servicio

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.remote.servicio.ServicioDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentServicioBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServiceViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServicioViewModel
import es.alvarorodriguez.lastijerasdenuria.repositories.servicio.ServicioRepoImpl
import es.alvarorodriguez.lastijerasdenuria.ui.servicio.adapter.ServicioAdapter

class ServicioFragment : Fragment(R.layout.fragment_servicio) {

    private lateinit var binding: FragmentServicioBinding

    private val viewModel by viewModels<ServicioViewModel> {
        ServiceViewModelFactory(
            ServicioRepoImpl(
                ServicioDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServicioBinding.bind(view)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_servicioFragment_to_addServicioFragment)
        }

        getServices()
    }

    private fun getServices() {
        viewModel.getServices().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvServices.adapter = ServicioAdapter(result.data)
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