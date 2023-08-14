# Part 1: Build the app using Maven
FROM maven:3.9.3-amazoncorretto-20

ARG EHB_DB_USER
ENV EHB_DB_USER=$EHB_DB_USER

## download dependencies
ADD pom.xml /
#RUN mvn clean install
RUN mvn verify clean
## build after dependencies are down so it wont redownload unless the POM changes
ADD . /
RUN mvn package

# Part 2: use the JAR file used in the first part and copy it across ready to RUN
FROM openjdk:21-jdk-slim
WORKDIR /root/
## COPY packaged JAR file and rename as app.jar
## → this relies on your MAVEN package command building a jar
## that matches *-jar-with-dependencies.jar with a single match
COPY --from=0 /target/campus_manager*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
