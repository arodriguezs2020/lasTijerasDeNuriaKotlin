package es.alvarorodriguez.lastijerasdenuria.ui.reserva.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.alvarorodriguez.lastijerasdenuria.core.BaseViewHolder
import es.alvarorodriguez.lastijerasdenuria.core.TimeUtils
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.databinding.ReservaItemBinding
import java.sql.Time

class ReservaAdapter(private val reservationList: List<Reserva>): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ReservaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is ReservaAdapter.ReservationViewHolder -> holder.bind(reservationList[position])
        }
    }

    override fun getItemCount(): Int = reservationList.size

    private inner class ReservationViewHolder(val binding: ReservaItemBinding): BaseViewHolder<Reserva>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: Reserva) {
            binding.nameComplete.text = item.name + " " + item.surname
            binding.phone.text = item.phone
            addReservationTimeStamp(item)
            binding.serviceName.text = item.servicio?.name
            binding.servicePrice.text = "${item.servicio?.price} €"
        }

        private fun addReservationTimeStamp(item: Reserva) {
            val created_at = (item.create_at?.time?.div(1000L))?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.createAt.text = created_at
        }
    }
}