package es.alvarorodriguez.lastijerasdenuria.presentation.servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.repositories.servicio.ServicioRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ServicioViewModel(private val repo: ServicioRepo): ViewModel() {
    fun addService(servicio: Servicio) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.addService(servicio)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getServices() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        kotlin.runCatching {
            repo.getServices()
        }.onSuccess { serviceList ->
            emit(serviceList)
        }.onFailure { error ->
            emit(Resource.Failure(Exception(error.message)))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ServiceViewModelFactory(private val repo: ServicioRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ServicioViewModel(repo) as T
    }
}