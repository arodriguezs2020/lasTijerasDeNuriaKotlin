package es.alvarorodriguez.lastijerasdenuria.repositories.reserva

import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio

interface ReservaRepo {
    suspend fun addReservation(reserva: Reserva, servicio: Servicio)
    suspend fun getReservations(): Resource<List<Reserva>>
}