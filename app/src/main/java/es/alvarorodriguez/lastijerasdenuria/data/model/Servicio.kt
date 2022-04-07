package es.alvarorodriguez.lastijerasdenuria.data.model

import com.google.firebase.firestore.Exclude

data class Servicio(
    @Exclude @JvmField
    val id: Int = 1,
    val name: String = "",
    val price: String = ""
)
