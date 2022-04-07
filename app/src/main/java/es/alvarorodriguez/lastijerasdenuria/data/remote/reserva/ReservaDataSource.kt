package es.alvarorodriguez.lastijerasdenuria.data.remote.reserva

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import es.alvarorodriguez.lastijerasdenuria.core.Resource
import es.alvarorodriguez.lastijerasdenuria.data.model.Reserva
import es.alvarorodriguez.lastijerasdenuria.data.model.Servicio
import kotlinx.coroutines.tasks.await

class ReservaDataSource {

    suspend fun addReservation(reserva: Reserva, servicio: Servicio) {

        val res = Reserva(
            name = reserva.name, surname = reserva.surname,
            phone = reserva.phone, servicio = servicio
        )

        FirebaseFirestore.getInstance().collection("reserva").add(res).await()
    }

    suspend fun getReservations(): Resource<List<Reserva>> {
        val reservaList = mutableListOf<Reserva>()
        val reservations = FirebaseFirestore.getInstance().collection("reserva").orderBy("create_at", Query.Direction.DESCENDING).get().await()
        for (reservation in reservations.documents) {
            reservation.toObject(Reserva::class.java)?.let { reserv ->
                reservaList.add(reserv)
            }
        }
        return Resource.Success(reservaList)
    }
}