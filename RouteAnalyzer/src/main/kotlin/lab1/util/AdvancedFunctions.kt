package lab1.util

import lab1.model.*
import kotlin.math.exp
import kotlin.math.PI

object AdvancedFunctions {

    private val util = MandatoryFunctions

    fun timeIntervalBetweenWaypoints(wayPoints: List<WayPoint>): TimeIntervalBetweenWaypoints {
        var firsPoint = wayPoints.first()
        var lastPoint = wayPoints.last()
        wayPoints.forEach {
            if(it.timestamp < firsPoint.timestamp){
                firsPoint = it
            }
            if(it.timestamp > lastPoint.timestamp){
                lastPoint = it
            }
        }
        return TimeIntervalBetweenWaypoints(firsPoint, lastPoint, lastPoint.timestamp-firsPoint.timestamp)
    }

    fun waypointDensity(centre:WayPoint, wayPoints:List<WayPoint>, radius:Radius):WaypointDensity{
        val area = exp(radius.radius)*PI
        var pointsInsideTheArea = 0.0
        wayPoints.forEach {
            if((util.distanceBetweenPoints(it, centre, radius)*radius.factor)<radius.radius) pointsInsideTheArea++
        }
        return WaypointDensity(centre, area, pointsInsideTheArea/area)
    }

    fun boundingBox(waypoints:List<WayPoint>):BoundingBox{
        var maxX = -180.0
        var maxY = -90.0
        var minX = 180.0
        var minY = 90.0
        waypoints.forEach {
            if(maxX<it.longitude)maxX=it.longitude
            if(maxY<it.latitude)maxY=it.latitude
            if(minX>it.longitude)minX=it.longitude
            if(minY>it.latitude)minY=it.latitude
        }
        var pointA = WayPoint(0.0, minX, maxY)
        val pointB = WayPoint(0.0, maxX, maxY)
        var pointC = WayPoint(0.0, maxX, minY)
        val pointD = WayPoint(0.0, minX, minY)
        var area = (maxX-minX)*(maxY-minY)
        if(maxX-minX>180.0){
            val temp = pointA
            pointA = pointC
            pointC = temp
            area = (360-(maxX-minX))*(maxY-minY)
        }
        return BoundingBox(pointA, pointB, pointC, pointD, area)
    }

    fun waypointCluster(waypoints:List<WayPoint>, radius: Radius):WaypointDensity{
        var maxDensity = 0.0
        var centre = waypoints[0]
        waypoints.forEach {
            val tempDensity = this.waypointDensity(it, waypoints, radius)
            if(maxDensity < tempDensity.pointsPerSquareKm) maxDensity = tempDensity.pointsPerSquareKm
            centre = it
        }
        return WaypointDensity(centre, radius.radius, maxDensity)
    }

}