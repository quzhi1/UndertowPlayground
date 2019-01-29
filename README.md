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
docker build -t test-image .
docker run -P test-image
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