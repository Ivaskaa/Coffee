FROM openjdk:11
ADD target/coffee.jar coffee.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "coffee.jar"]
