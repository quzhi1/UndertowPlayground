# UndertowPlayground
This is a playground for undertwo server

## Run locally

Build it first

```mvn clean install```

To start the server

```mvn exec:java```

## Run docker

Build it first

```
mvn clean install
docker build -t undertow-playground .
# docker run -P undertow-playground -p 8080:8080
docker run -d -p 8080:8080 -t undertow-playground
```

To find the port
```
docker ps
docker port <image_id>
```

To kill docker 
```
docker ps
docker kill <image_id>
docker rm <image_id>
```

To login
```
docker exec -it <image_id> bash
```