package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class MaxDistanceFromStart(
    val waypoint: WayPoint,
    val distanceKm: Double
)
