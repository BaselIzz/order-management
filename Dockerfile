FROM autocomplete/amazoncorretto17-git
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
