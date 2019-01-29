# UndertowPlayground
This is a playground for undertow server

## Run docker

Build it first

```
mvn clean install
docker build -t undertow-playground .
docker run -d -p 8080:8080 -t undertow-playground
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