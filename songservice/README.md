# Command to run:
gradle build
java -jar build/libs/songservice-0.0.1-SNAPSHOT.jar

# Comand to run on Docker:
docker build -t yana/song-service:latest .
docker images
docker run -p 8081:8081 yana/song-service:latest
