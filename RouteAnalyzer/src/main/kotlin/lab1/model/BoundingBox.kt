package lab1.model

import kotlinx.serialization.Serializable

@Serializable
data class BoundingBox(
    val pointA: WayPoint,
    val pointB: WayPoint,
    val pointC: WayPoint,
    val pointD: WayPoint,
    val area: Double
)
