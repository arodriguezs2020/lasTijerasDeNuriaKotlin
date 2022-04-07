package es.alvarorodriguez.lastijerasdenuria.repositories.servicio

import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio

interface ServicioRepo {
    suspend fun addService(servicio: Servicio)
    suspend fun getServices(): Resource<List<Servicio>>
}