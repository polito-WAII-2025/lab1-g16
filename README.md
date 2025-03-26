
# lab1-wa2025

This project processes waypoint data from a CSV file and generates a set of geospatial metrics, including:
- Maximum distance from the start,
- Most frequented area based on waypoints outside a defined geofence,
- List of waypoints outside the geofence.

The application is built in Kotlin and uses libraries for CSV parsing, JSON serialization, and YAML parsing. The project also supports Docker for easy containerization.

## Features

### 1. **Maximum Distance from Start**:
   - Calculates the maximum distance between the starting point (first waypoint) and all other waypoints in the dataset.

### 2. **Most Frequented Area**:
   - Identifies the most frequented area based on waypoints that lie outside a geofence defined by a radius.

### 3. **Waypoints Outside Geofence**:
   - Identifies how many waypoints are located outside a geofence, and lists those waypoints.
   
### 4. **Distance Calculation**:
   - The application calculates distances using latitude and longitude, approximating distances to kilometers by multiplying by a factor of 111.

### 5. **Input**:
   - The application reads data from two files:
     - `waypoints.csv`: Contains the data of waypoints (timestamp, latitude, longitude).
     - `custom-parameters.yml`: Contains a configuration for the radius of the geofence.
   
### 6. **Output**:
   - The application generates a detailed `output_advanced.json` file containing:
     - **maxDistanceFromStart**: The maximum distance between the start and any waypoint.
     - **mostFrequentedArea**: The central waypoint with the most waypoints outside the geofence.
     - **waypointsOutsideGeofence**: A list of waypoints outside the geofence with the count and details.

## Requirements

- **Kotlin**: Version 1.6 or higher
- **Gradle**: Version 7.3 or higher
- **Docker**: (Optional for containerized deployment)

## Setup Instructions

### 1. Clone the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/your-username/lab1-wa2025.git
cd lab1-wa2025
```

### 2. Build the Project

#### Using Gradle:

If you're running the project locally, build the application with Gradle:

```bash
./gradlew build
```

This will create the `RouteAnalyzer-1.0-SNAPSHOT-all.jar` file inside the `build/libs/` directory.

#### Docker Setup:

To run the application in a Docker container, build the Docker image:

```bash
docker build -t kotlin-app .
```

### 3. Configure Files

Make sure you have the following files:

- **waypoints.csv**: Contains the data of waypoints (timestamp, latitude, longitude).
- **custom-parameters.yml**: A configuration file for the geofence radius.

The `custom-parameters.yml` should look like this:

```yaml
conf:
  radius: 1.0  # Radius in kilometers for geofence
  unit: km     # Unit of distance (optional, currently supports 'km')
```

### 4. Run the Application Locally

To run the application locally, use the following command:

```bash
java -jar build/libs/RouteAnalyzer-1.0-SNAPSHOT-all.jar
```

Ensure that the path to the `custom-parameters.yml` and `waypoints.csv` are correctly set in the code.

### 5. Run the Application with Docker

To run the application using Docker, first build the Docker image and then run the container:

```bash
docker run -d --name my-kotlin-app -v $(PWD)/waypoints.csv:/app/waypoints.csv -v $(PWD)/custom-parameters.yml:/app/custom-parameters.yml kotlin-app
```

This command mounts the `waypoints.csv` and `custom-parameters.yml` from your host system into the container.

### 6. Output

The application will generate a `output.json` file that will contain the calculated metrics. The JSON file will be saved in the container's file system or, if mounted correctly, in your project directory.

## Output Format

The output JSON file will look like this:

```json
{
  "maxDistanceFromStart": {
    "waypoint": {
      "timestamp": 1741958525987,
      "latitude": 52.26812,
      "longitude": 10.36037
    },
    "distanceKm": 29117.33
  },
  "mostFrequentedArea": {
    "centralWaypoint": {
      "timestamp": 1741958546344,
      "latitude": 51.35116,
      "longitude": 9.7542
    },
    "areaRadiusKm": 1.0,
    "entriesCount": 313
  },
  "waypointsOutsideGeofence": {
    "centralWaypoint": {
      "timestamp": 1741958525987,
      "latitude": 52.26812,
      "longitude": 10.36037
    },
    "areaRadiusKm": 1.0,
    "count": 312,
    "waypoints": [
      {
        "timestamp": 1741958526051,
        "latitude": 52.26441,
        "longitude": 10.38272
      },
      {
        "timestamp": 1741958526115,
        "latitude": 52.25693,
        "longitude": 10.40929
      }
      // More waypoints
    ]
  }
}
```

### 7. Stopping and Removing the Docker Container

To stop and remove the running container:

```bash
docker stop my-kotlin-app
docker rm my-kotlin-app
```

## Optional Features

Here are some additional features you can implement based on the data you have:

1. **Time Interval Between Waypoints**: Calculate and add the time differences between each consecutive waypoint.
2. **Waypoint Density**: Compute the density of waypoints in a certain geographical area.
3. **Bounding Box**: Calculate the bounding box (min/max latitude and longitude) that encapsulates all the waypoints.
4. **Waypoint Clusters**: Implement clustering algorithms (e.g., DBSCAN) to detect areas with high density of waypoints.
5. **Altitude**: If available, track and output the altitude changes over time.

Feel free to add any additional features or modify the existing code to suit your needs.

## Conclusion

This Kotlin-based project processes waypoint data, calculates advanced geospatial metrics, and produces a JSON output. The application is packaged using Docker for easy deployment and can accept custom YAML configuration files during runtime. This setup allows for easy scaling and portability across different environments.

