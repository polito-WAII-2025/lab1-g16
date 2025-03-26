package lab1.controller


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.serialization.encodeToString
import kotlin.math.max
import kotlin.math.pow
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml


@Serializable
data class Point(val timestamp: Double, val latitude: Double, val longitude: Double)

@Serializable
data class MaxDistanceFromStart(
    val waypoint: Point,
    val distanceKm: Double
)

@Serializable
data class MostFrequentedArea(
    val centralWaypoint: Point,
    val areaRadiusKm: Double,
    val entriesCount: Int
)

@Serializable
data class WaypointsOutsideGeofence(
    val centralWaypoint: Point,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<Point>
)

@Serializable
data class OutputJson(
    val maxDistanceFromStart: MaxDistanceFromStart,
    val mostFrequentedArea: MostFrequentedArea,
    val waypointsOutsideGeofence: WaypointsOutsideGeofence
)

data class Configurazione(
    val raggio: Int,
    val unita: String
)




fun main(args: Array<String>) {

    val file = File("../evaluation/waypoints.csv").readText()
    val points = csvReader{delimiter = ';'}.readAll(file).mapNotNull { row ->
        if (row.size >= 3) {
            Point(
                timestamp = row[0].toDouble(),   // first column (timestamp)
                latitude = row[1].toDouble(),    // second column (laatitude)
                longitude = row[2].toDouble()    // third column (longitude)
            )
        } else {
            null  // ignore lines with less than three parameters
        }
    }
    /*
    all distances are referred to the grades of latitude and longitude
    to convert them into km, multiply for a factor of 111
    in reality, for the longitude it changes due to the curvature of the globe,
    but it is a fair approximation, especially in the tropical region
     */
    //we use as a centre the first point of the list if not written in the args, while the radius is obtained by the file yml
    val inputStream = File("../evaluation/custom-parameters.yml").inputStream()
    val data: Map<String, Any> = Yaml().load(inputStream)
    val conf = data["conf"] as Map<String, Any>
    val radius = conf["radius"] as Double
    val centre;
    if(args.isNotEmpty() || args.size<3){
        centre = Point(args[0], args[1], args[2])
    }else{
        centre = points[0];
    }
    val output = OutputJson(maxDistanceFromStart(points, centre), mostFrequentedArea(points, radius), waypointsOutsideGeofence(points, centre, radius))
    val outputFile = File("../evaluation/output.json")
    outputFile.writeText(Json.encodeToString(output))
}

fun distanceBetweenPoints(p1:Point,p2:Point):Double {
    var x1: Double = p1.latitude
    var y1: Double = p1.longitude
    var x2: Double = p2.latitude
    var y2: Double = p2.longitude
    return Math.sqrt(((x1 - x2).pow(2) + (y1 - y2).pow(2)))*111
}

fun maxDistanceFromStart(points: List<Point>, startingPoint: Point):MaxDistanceFromStart{
    var maxDistance:Double = 0.0;
    points.forEach {
        var distance = distanceBetweenPoints(startingPoint, it)
        if(distance > maxDistance){maxDistance = distance}
    }
    return MaxDistanceFromStart(startingPoint, distanceKm = maxDistance*111)
}

fun mostFrequentedArea(points: List<Point>, radius: Double):MostFrequentedArea{
    var res = points[0]
    var externs = points.size
    points.forEach {
        if(waypointsOutsideGeofence(points, it, radius).count<externs){
            res = it
        }
    }
    return MostFrequentedArea(res, radius, externs)
}

fun waypointsOutsideGeofence(points: List<Point>, centre: Point, radius: Double):WaypointsOutsideGeofence{
    var i = 0;
    var list = mutableListOf<Point>()
    points.forEach {
        if(distanceBetweenPoints(it, centre)>radius){
            i++
            list.add(it)
        }
    }
    return WaypointsOutsideGeofence(centre, radius, i, list)
}