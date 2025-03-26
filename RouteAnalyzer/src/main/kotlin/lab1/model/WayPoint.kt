package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class WayPoint(
    val timestamp: Double,
    val latitude: Double,
    val longitude: Double
)
