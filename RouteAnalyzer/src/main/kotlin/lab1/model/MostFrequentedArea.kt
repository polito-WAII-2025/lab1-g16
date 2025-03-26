package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class MostFrequentedArea(
    val centralWaypoint: WayPoint,
    val areaRadiusKm: Double,
    val entriesCount: Int
)
