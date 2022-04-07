package es.alvarorodriguez.lastijerasdenuria.repositories.reserva

import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import es.alvarorodriguez.lastijerasdenuria.data.remote.reserva.ReservaDataSource

class ReservaRepoImpl(private val dataSource: ReservaDataSource) : ReservaRepo {
    override suspend fun addReservation(reserva: Reserva, servicio: Servicio) =
        dataSource.addReservation(reserva, servicio)

    override suspend fun getReservations(): Resource<List<Reserva>> =
        dataSource.getReservations()
}