package es.alvarorodriguez.lastijerasdenuria.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    var images = arrayListOf(
        R.drawable.imagen0,
        R.drawable.imagen1,
        R.drawable.imagen2
    )

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        carousel()
        clickButtons()
    }

    private fun clickButtons() {
        binding.btnServicios.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_servicioFragment)
        }
        binding.btnReservas.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reservationFragment)
        }
    }

    private fun carousel() {
        binding.carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return images.size
            }

            override fun populate(view: View, index: Int) {
                if (view is ImageView) {
                    view.setImageResource(images[index])
                }
            }

            override fun onNewItem(index: Int) {
                binding.carousel.findViewById<ImageView>(index)
            }
        })
    }
}