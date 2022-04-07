package es.alvarorodriguez.lastijerasdenuria.ui.reserva

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.remote.reserva.ReservaDataSource
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentReservationBinding
import es.alvarorodriguez.lastijerasdenuria.presentation.reserva.ReservaViewModel
import es.alvarorodriguez.lastijerasdenuria.presentation.reserva.ReservaViewModelFactory
import es.alvarorodriguez.lastijerasdenuria.repositories.reserva.ReservaRepoImpl
import es.alvarorodriguez.lastijerasdenuria.ui.reserva.adapter.ReservaAdapter

class ReservationFragment : Fragment(R.layout.fragment_reservation) {

    private lateinit var binding: FragmentReservationBinding

    private val viewModel by viewModels<ReservaViewModel> {
        ReservaViewModelFactory(
            ReservaRepoImpl(
                ReservaDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReservationBinding.bind(view)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_addReservaFragment)
        }

        getReservations()
    }

    private fun getReservations() {
        viewModel.getReservations().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvReservation.adapter = ReservaAdapter(result.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}