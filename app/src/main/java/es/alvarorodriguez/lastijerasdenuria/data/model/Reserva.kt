package es.alvarorodriguez.lastijerasdenuria.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Reserva(
    @Exclude @JvmField
    val id: Int = 1,
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    @ServerTimestamp
    val create_at: Date? = null,
    val servicio: Servicio? = null
)
