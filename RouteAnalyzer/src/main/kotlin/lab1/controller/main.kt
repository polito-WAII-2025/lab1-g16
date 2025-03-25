package lab1.controller


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlin.math.max
import kotlin.math.pow


@Serializable
data class Point(val timestamp: Double, val latitude: Double, val longitude: Double)


fun main() {

    val file = File("waypoints.csv").readText()
    val points = csvReader{delimiter = ';'}.readAll(file).mapNotNull { row ->
        if (row.size >= 3) {
            Point(
                timestamp = row[0].toDouble(),   // Seconda colonna (timestamp)
                latitude = row[1].toDouble(),    // Prima colonna (latitudine)
                longitude = row[2].toDouble()    // Terza colonna (longitudine)
            )
        } else {
            null  // Ignora righe con meno di 3 colonne
        }
    }
    /*
    all distances are referred to the latitude and longitude positions
    to convert them into km, multiply for a factor of 111
    in reality, it changes due to the curvature of the globe,
    but it is a fair approximation, especially in the tropical region
     */
    points.forEach{(println(it))}
    println(distanceBetweenPoints(points[0], points[1]))
    println(maxDistanceFromStart(points, points[0]))
    println(mostFrequentedArea(points, 1.0))

}

fun distanceBetweenPoints(p1:Point,p2:Point):Double {
    var x1: Double = p1.latitude
    var y1: Double = p1.longitude
    var x2: Double = p2.latitude
    var y2: Double = p2.longitude
    return Math.sqrt(((x1 - x2).pow(2) + (y1 - y2).pow(2)))
}

fun maxDistanceFromStart(points: List<Point>, startingPoint: Point):Double{
    var maxDistance:Double = 0.0;
    points.forEach {
        var distance = distanceBetweenPoints(startingPoint, it)
        if(distance > maxDistance){maxDistance = distance}
    }
    return maxDistance
}

fun mostFrequentedArea(points: List<Point>, radius: Double):Point{
    var res = points[0]
    var externs = points.size
    points.forEach {
        if(waypointsOutsideGeofence(points, it, radius)<externs){
            res = it
        }
    }
    return res
}

fun waypointsOutsideGeofence(points: List<Point>, centre: Point, radius: Double):Int{
    var i = 0;
    points.forEach {
        if(distanceBetweenPoints(it, centre)>radius){
            i++
        }
    }
    return i
}