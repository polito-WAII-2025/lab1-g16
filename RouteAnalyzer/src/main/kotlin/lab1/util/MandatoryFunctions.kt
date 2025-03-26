package lab1.util

import lab1.model.*
import kotlin.math.pow
import kotlin.math.sqrt

object MandatoryFunctions {


    fun distanceBetweenPoints(p1:WayPoint,p2:WayPoint, radius:Radius):Double {
        val x1: Double = p1.latitude
        val y1: Double = p1.longitude
        val x2: Double = p2.latitude
        val y2: Double = p2.longitude
        return sqrt(((x1 - x2).pow(2) + (y1 - y2).pow(2))) *radius.factor
    }

    fun maxDistanceFromStart(points: List<WayPoint>, startingPoint: WayPoint, radius:Radius):MaxDistanceFromStart{
        var maxDistance = 0.0
        points.forEach {
            val distance = distanceBetweenPoints(startingPoint, it, radius)
            if(distance > maxDistance) maxDistance = distance
        }
        return MaxDistanceFromStart(startingPoint, maxDistance)
    }

    fun mostFrequentedArea(points: List<WayPoint>, radius: Radius):MostFrequentedArea{
        var res = points[0]
        var externals = points.size
        points.forEach {
            if(waypointsOutsideGeofence(points, it, radius).count<externals){
                res = it
                externals = waypointsOutsideGeofence(points, it, radius).count
            }
        }
        return MostFrequentedArea(res, radius.radius, externals)
    }

    fun waypointsOutsideGeofence(points: List<WayPoint>, centre: WayPoint, radius: Radius): WaypointsOutsideGeofence {
        val outsideList = points.filter { distanceBetweenPoints(it, centre, radius) > radius.radius }
        return WaypointsOutsideGeofence(centre, radius.radius, outsideList.size, outsideList)
    }

}