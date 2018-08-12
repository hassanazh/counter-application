***Counter Application Rest API***

This application is used as a REST API to create, increment and get a counter from any frontend service.

**Minimum requirements**

- This project requires JVM 1.8
- Maven 3 or higher version, if not installed then this can be installed from
    - brew install maven (in Mac OS)
- Import this project in any IDE e.g. Intellij IDEA or eclipse.
- In terminal go to project directory and run `mvn jetty:run`

**Rest APIs**

Five rest calls are supported by the application:
    
1. Create a counter (POST request): `http://localhost:8080/counter/{COUNTER-NAME}`
2. Increment a Counter (PUT request): `http://localhost:8080/counter/{COUNTER-NAME}`
3. Get a Counter (Get request): `http://localhost:8080/counter/{COUNTER-NAME}`
4. Get all Counters (Get request): `http://localhost:8080/counter`
5. Delete all Counters (Delete request):  `http://localhost:8080/counter`

p.s.: Delete request is added only to clear counter for tests.

**Note**

This is my first time with `Jersey` framework. I have never worked with `Jersey` before, this is my first time.
Previously, I have worked with `AKKA http`and `Spring`. 
