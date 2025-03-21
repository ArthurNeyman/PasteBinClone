FROM openjdk:24-ea-20-jdk-oraclelinux9
ARG JAR_FILE=PasteBinClone/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]