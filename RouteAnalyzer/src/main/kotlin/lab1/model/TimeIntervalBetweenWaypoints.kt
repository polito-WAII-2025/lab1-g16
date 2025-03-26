package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class TimeIntervalBetweenWaypoints(
    val firstWayPoint: WayPoint,
    val lastWayPoint: WayPoint,
    val totalTime: Double
)
