package es.alvarorodriguez.lastijerasdenuria.data.remote.servicio

import com.google.firebase.firestore.FirebaseFirestore
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import kotlinx.coroutines.tasks.await

class ServicioDataSource {

    suspend fun addService(servicio: Servicio) {
        FirebaseFirestore.getInstance().collection("servicio").add(servicio).await()
    }

    suspend fun getServices(): Resource<List<Servicio>> {
        val serviceList = mutableListOf<Servicio>()
        val services = FirebaseFirestore.getInstance().collection("servicio").get().await()
        for (service in services.documents) {
            service.toObject(Servicio::class.java)?.let { servi ->
                serviceList.add(servi)
            }
        }
        return Resource.Success(serviceList)
    }
}