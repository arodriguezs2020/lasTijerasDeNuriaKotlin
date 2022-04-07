package es.alvarorodriguez.lastijerasdenuria.repositories.servicio

import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.data.remote.servicio.ServicioDataSource

class ServicioRepoImpl(private val dataSource: ServicioDataSource) : ServicioRepo {
    override suspend fun addService(servicio: Servicio) = dataSource.addService(servicio)
    override suspend fun getServices(): Resource<List<Servicio>> = dataSource.getServices()
}
