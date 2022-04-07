package es.alvarorodriguez.lastijerasdenuria.presentation.reserva

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.repositories.reserva.ReservaRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ReservaViewModel(private val repo: ReservaRepo) : ViewModel() {

    fun addReservation(reserva: Reserva, servicio: Servicio) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.addReservation(reserva, servicio)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getReservations() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        kotlin.runCatching {
            repo.getReservations()
        }.onSuccess { reservationList ->
            emit(reservationList)
        }.onFailure { error ->
            emit(Resource.Failure(Exception(error.message)))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ReservaViewModelFactory(private val repo: ReservaRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReservaViewModel(repo) as T
    }
}