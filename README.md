# Skeleton for Spring Boot based REST services (proof of concept)

# Contents

* Lombok
* MapStruct
* OpenAPI 3 (Swagger) annotations with Swagger UI
* Spring Boot 3.0.6
* Spring Cloud 2022.0.2

# Preconditions

Requires a PostgreSQL database connection to work. You can set up your in `person/src/main/resources/application.yml`.
Or alternatively you can run one with the preconfigured connection parameters in Docker:

```
docker run -it -p 5432:5432 -e POSTGRES_DB=dev -e POSTGRES_USER=dev -e POSTGRES_PASSWORD=dev postgres:alpine 
```

# ToDo

* Implement other storage models
    * ElasticSearch
    * MongoDB
* Implement non-string search criteria
* Add optimistic locking
* Add OAuth2
* Move to the cloud: Spring Cloud Kubernetes
