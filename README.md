# Game-Backlog-Tracker

---

Kotlin version: 2.4.21  
Java version: 21

## Starting the app

From the root directory of the repository, run the following command

MacOS:
```
./gradlew run
```

Windows:
```
.\gradlew.bat run
```

Alternatively, run Main.kt through your IDE (tested on IntelliJ)

The app serves requests on `http://localhost:8080` by default. OpenAPI specification can be found at `src/main/resources/openapi/documentation.yaml`

### Swagger UI served at `http://localhost:8080/swagger`