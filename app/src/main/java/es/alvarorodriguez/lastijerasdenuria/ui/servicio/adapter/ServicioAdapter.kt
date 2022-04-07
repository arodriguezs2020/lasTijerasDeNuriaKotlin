package es.alvarorodriguez.lastijerasdenuria.ui.servicio.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.alvarorodriguez.lastijerasdenuria.core.BaseViewHolder
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.databinding.ServiceItemBinding

class ServicioAdapter(private val serviceList: List<Servicio>): RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ServiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is ServiceViewHolder -> holder.bind(serviceList[position])
        }
    }

    override fun getItemCount(): Int = serviceList.size

    private inner class ServiceViewHolder(val binding: ServiceItemBinding): BaseViewHolder<Servicio>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: Servicio) {
            binding.name.text = item.name
            binding.price.text = "${item.price} €"
        }
    }
}