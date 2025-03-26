package lab1.repository

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab1.model.AdditionalOutput
import lab1.model.OutputJson
import lab1.model.Radius
import lab1.model.WayPoint
import lab1.util.AdvancedFunctions
import lab1.util.MandatoryFunctions
import org.yaml.snakeyaml.Yaml
import java.io.File

object InputOutput {

    private val yaml = Yaml()
    private val util = MandatoryFunctions
    private val additionalUtils = AdvancedFunctions

    fun getRadius():Radius{
        val inputStream = File("../evaluation/custom-parameters.yml").inputStream()
        val data: Map<String, Any> = yaml.load(inputStream)
        val conf = data["conf"] as Map<String, Any>
        val radius = conf["radius"] as Double
        val unit = conf["unit"] as String
        val factor = conf["factor"] as Double
        return Radius(radius, unit, factor)
    }

    fun getWayPoints():List<WayPoint>{
        val file = File("../evaluation/waypoints.csv").readText()
        val points = csvReader{delimiter = ';'}.readAll(file).mapNotNull { row ->
            if (row.size >= 3) {
                WayPoint(
                    timestamp = row[0].toDouble(),   // first column (timestamp)
                    latitude = row[1].toDouble(),    // second column (latitude)
                    longitude = row[2].toDouble()    // third column (longitude)
                )
            } else {
                null  // ignore lines with less than three parameters
            }
        }
        return points
    }


    fun mandatoryOutput(points: List<WayPoint>, centre: WayPoint, radius: Radius){
        val output = OutputJson(util.maxDistanceFromStart(points, centre, radius), util.mostFrequentedArea(points, radius), util.waypointsOutsideGeofence(points, centre, radius))
        val outputFile = File("../evaluation/output.json")
        outputFile.writeText(Json.encodeToString(output))
    }

    fun additionalOutput(points: List<WayPoint>, centre: WayPoint, radius: Radius){
        val output = AdditionalOutput(additionalUtils.timeIntervalBetweenWaypoints(points), additionalUtils.waypointDensity(centre, points, radius), additionalUtils.boundingBox(points), additionalUtils.waypointCluster(points, radius))
        val outputFile = File("../evaluation/additionalOutput.json")
        outputFile.writeText(Json.encodeToString(output))
    }

}