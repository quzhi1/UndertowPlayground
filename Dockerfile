FROM openjdk:8-jre

COPY ./target/*-with-dependencies.jar /jars/service-jar.jar
EXPOSE 8080
CMD java -cp /jars/service-jar.jar com.quzhi1.undertowplayground.Main