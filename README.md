# Cucumber POC

Demo of a simple REST service that is secured by [Cucumber](https://cucumber.io/) tests.

As an example the Cucumber test specifications are both provided in English and German,
see `src/test/resources/features/*.feature`.

## Run

```bash
./gradlew bootRun
```

## Inspect

* [Swagger UI at http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* [H2 database console at http://localhost:8080/h2/](http://localhost:8080/h2/) (*) 

(*) Apply the JDBC URL `"jdbc:h2:mem:test"` for the H2 database console.

## Cucumber Test

Initiate the Cucumber test from a second terminal window as the Cucumber tests requires a running instance of the REST service. 

```bash
./gradlew cucumberTest
```

See the generated Cucumber HTML report at `build/reports/cucumber.html`.

## Credits

Credits to the GitHub project [cucumber-spring-boot-kotlin](https://github.com/realpacific/cucumber-spring-boot-kotlin)
by Ray Wenderlich.

Dirk Duehr, July 2024