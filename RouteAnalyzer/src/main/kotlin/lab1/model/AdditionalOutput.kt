package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class AdditionalOutput(
    val timeIntervalBetweenWaypoints: TimeIntervalBetweenWaypoints,
    val waypointDensity: WaypointDensity,
    val boundingBox: BoundingBox,
    val waypointCluster: WaypointDensity
)