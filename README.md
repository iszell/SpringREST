# Skeleton for Spring Boot based REST services (proof of concept)

# Parts

## bom

Dependency and plugin management for the whole project. You should not use any dependencies/plugins which are not
declared and configured here.

## root-api

The API root which you can extend. The main API is the generic `MaintenanceAPI` which has a default implementation of
returning HTTP 501 - Not implemented.

## core

The core framework functions:

* exception handling `ExceptionHandlerControllerAdvice`
* a (n abstract) default implementation for `MaintenanceAPI` - `AbstractMaintenanceController`
* a generic data storage service for the controller - `MaintenanceService`
* a generic JPA implementation for the `MaintenanceService` - `AbstractJpaMaintenanceService`
* a root entity model - `BaseEntity`
* a JPA repository root - `BaseJpaRepository`
* a generic MapStruct mapper for mapping between the API DTOs and the stored entities - `EntityMapper`
* `resources/changelog-master.xml` - LiquiBase setup for maintaining database lifecycle

and of course several other classes to support the above

## person-api

A sample API to store simple data about people:

* the maintenance API for the Person object - `PersonAPI`
* the data model of a person - `PersonDTO`

## person

A sample implementation of the Person API:

* the entity -`Person`
* the JPA repository - `PersonRepository`
* model mapper - `PersonMapper`
* service for data management - `PersonService`
* database model for a person - `resources/modulechangelog/*.xml`
* a sample Spring Boot app to demonstrate the operation - `PersonApplication`

# Components used

* Lombok
* MapStruct
* Spring Boot 3.0.6
* Spring Cloud 2022.0.2
* SpringDoc OpenAPI 3 (Swagger) annotations with Swagger UI

# Preconditions

Requires a PostgreSQL database connection to work. You can set up your in `person/src/main/resources/application.yml`.
Or alternatively you can run one with the preconfigured connection parameters in Docker:

```
docker run -it -p 5432:5432 -e POSTGRES_DB=dev -e POSTGRES_USER=dev -e POSTGRES_PASSWORD=dev postgres:alpine 
```

# Running the sample

1. run `mvn clean package` for the bom
2. `cd person`
3. run `mvn spring-boot:run`
4. navigate your browser to <http://localhost:8080/swagger-ui.html>

# ToDo

* Implement other storage models
    * ElasticSearch
    * MongoDB
* Implement non-string search criteria
* Add optimistic locking
* Add OAuth2
* Move to the cloud: Spring Cloud Kubernetes
