package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class WaypointsOutsideGeofence(
    val centralWaypoint: WayPoint,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<WayPoint>
)
