package es.alvarorodriguez.lastijerasdenuria.ui.reserva

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.data.remote.reserva.ReservaDataSource
import es.alvarorodriguez.lastijerasdenuria.data.remote.servicio.ServicioDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentAddReservaBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.reserva.ReservaViewModel
import es.alvarorodriguez.lastijerasdenuria.presentation.reserva.ReservaViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServiceViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.presentation.servicio.ServicioViewModel
import es.alvarorodriguez.lastijerasdenuria.repositories.reserva.ReservaRepoImpl
import es.alvarorodriguez.lastijerasdenuria.repositories.servicio.ServicioRepoImpl

@Suppress("UNREACHABLE_CODE")
class AddReservaFragment : Fragment(R.layout.fragment_add_reserva), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentAddReservaBinding
    private var services : List<Servicio>? = null
    private var serviceSelected: Servicio? = null

    private val viewModelServices by viewModels<ServicioViewModel> {
        ServiceViewModelFactory(
            ServicioRepoImpl(
                ServicioDataSource()
            )
        )
    }

    private val viewModel by viewModels<ReservaViewModel> {
        ReservaViewModelFactory(
            ReservaRepoImpl(
                ReservaDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddReservaBinding.bind(view)
        val arrayStrings = arrayListOf<String>()
        btnReservationClickListener()
        getServices(arrayStrings)
    }

    private fun btnReservationClickListener() {
        binding.addReservation.setOnClickListener {

            val name = binding.editTextName.text.toString().trim()
            val surname = binding.editTextApellidos.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()

            val reserva = Reserva(name = name, surname = surname, phone = phone)

            val service = Servicio(name = serviceSelected!!.name, price = serviceSelected!!.price)

            addReservation(reserva, service)
        }
    }

    private fun addReservation(
        reserva: Reserva,
        service: Servicio
    ) {
        viewModel.addReservation(reserva, service).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_addReservaFragment_to_reservationFragment)
                    Toast.makeText(requireContext(), "Add Reserva", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun getServices(arrayStrings: ArrayList<String>) {
        viewModelServices.getServices().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    services = result.data

                    for (service in services!!) {
                        arrayStrings.add(service.name)
                    }
                    val adapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_item, arrayStrings)
                    binding.spinner.adapter = adapter
                    binding.spinner.onItemSelectedListener = this
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        serviceSelected = services?.get(pos)
        Toast.makeText(requireContext(), "Valor $serviceSelected", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}