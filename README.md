## MUSALA Drone Application

### Introduction

This assessment was completed using Spring Boot Framework ecosystem (WebFlux, R2DBC, Security) and  PostgreSQL RDBMS in Docker container.

| Property                           | Sample Value | Description                                            |
|------------------------------------|:------------:|--------------------------------------------------------|
| `musala.drones-scheduler-delay-ms` |    35000     | Delay in milliseconds between each Scheduler execution |
| `server.port`                      |     8080     | HTTP server port                                       |                               |



### Requirements
- java 17


### Build/Run instruction
- Start the [docker-compose.yml](docker-compose.yml) and connect to DB with credentials from compose file
- Run in console [init.sql](init.sql) script to create and base populate the DB
- After start the application we can use [Musala.postman_collection.json](Musala.postman_collection.json) for create user in database
and go to the routes from collection, NOTICE firstly we need create user, after them login and use generated JWT token to access to all routes




### Postman Collection Documentation: Musala

## Description
This Postman collection contains a set of HTTP requests for testing a web API. The collection consists of six items, each representing a single HTTP request with a name, request details, and an empty response.

## HTTP Requests
1. `CREATE_USER`: Request for creating a new user. Uses the POST method and sends a JSON request body with the parameters: username, password, and role to `http://127.0.0.1:8080/auth/signup`.
2. `LOGIN RQ`: Request for user authentication. Uses the POST method and sends a JSON request body with the parameters: username and password to `http://127.0.0.1:8080/auth/login`.
3. `REGISTER DRONE`: Request for creating a new drone. Uses the POST method and sends a JSON request body with the parameters of drone (see example in collection) to `http://127.0.0.1:8080/drones`.
4. `GET USER BY ID`: Request for fetching user information by ID. Uses the GET method and sends a request to `http://127.0.0.1:8080/users/1` with a Bearer token authorization header.
5. `DELETE USER BY ID`: Request for deleting a user by ID. Uses the DELETE method and sends a request to `http://127.0.0.1:8080/users/1` with a Bearer token authorization header.
6. `GET AVAILABLE DRONES`: Request for fetching information about available drones. Uses the GET method and sends a request to `http://127.0.0.1:8080/drone/available` with a Bearer token authorization header.
7. `GET DRONE MEDICATIONS` Request for fetching information about drone medications. Uses the GET method and sends a request to http://127.0.0.1:8080/drone/1/medications with a Bearer token authorization header.
8. `LOAD MEDICATIONS TO DRONE`: Request for loading medications onto a drone. Uses the POST method and sends a JSON request body with the parameters: droneId and medicationsIds to `http://127.0.0.1:8080/drone/load` with a Bearer token authorization header.

# NOTICE 
In LOGIN RQ we need to add the test script for copying generated token from Response body in future it simplifies work with request's

![test-script-login-rq.png](documentation%2Ftest-script-login-rq.png)

```
tests["Successful GET(login) request"] = responseCode.code === 200 || responseCode.code === 267;
var jsonData = JSON.parse(responseBody);
postman.setGlobalVariable("musala-token", jsonData.result.token);
```

And in all RQ added link to variable with token 

![img.png](documentation/header-rq.png)



## Authentication
To execute requests that require authentication, add an authorization header "Bearer token" to the request with the token.

**Note:** This collection is intended for testing the functionality of a web API related to user authentication and drone management. Each request contains additional information on how to use it.