package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class WaypointDensity(
    val centre: WayPoint,
    val radius: Double,
    val pointsPerSquareKm: Double
)
