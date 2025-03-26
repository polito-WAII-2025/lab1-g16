package lab1.controller


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.serialization.encodeToString
import lab1.model.WayPoint
import lab1.repository.InputOutput
import kotlin.math.max
import kotlin.math.pow
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml


fun main(args: Array<String>) {
    val io = InputOutput
    val wayPoints = io.getWayPoints()
    val radius = io.getRadius()
    var centre :WayPoint = wayPoints[0]
    if(args.isNotEmpty()){
        if(args.size!=3){
            println("Invalid params: Expected 3 arguments, found ${args.size}")
        }else {
            try {
                centre = WayPoint(args[0].toDouble(), args[1].toDouble(), args[2].toDouble())
            } catch (e: NumberFormatException) {
                println("Invalid number format in params: ${e.message}")
            } catch (e: Exception) {
                println("An unexpected error occurred: ${e.message}")
            }
        }
    }
    io.mandatoryOutput(wayPoints, centre, radius)
    io.additionalOutput(wayPoints, centre, radius)
}

