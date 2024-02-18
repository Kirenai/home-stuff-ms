# Home Stuff Microservice

This backend project is used to store, manage and retrieve household items. Have a way of knowing if household food is
available for consumption or for provisioning.

# Tecnologies

* Maven
* Java
* DBMS MySQL
* Git
* Spring

# Dependencies

* Spring Boot
    * Spring Framework
    * Spring WebFlux
    * Spring Security
    * Spring Data R2DBC
    * Spring Validation
    * Spring Cloud Gateway
    * Spring Cloud Eureka
    * Spring Cloud Config
* Lombok
* MySQL Drive R2DBC
* MapStruct
* Zipkin with Micrometer Brave
* KeyCloak

# Compilation

Home stuff use Maven as compilation system.

# Requirements

Have [Maven](https://maven.apache.org/download.cgi), [Git](https://git-scm.com)
and [JDK17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) installed.

# Commands

Clone repository

```github
https://github.com/Kirenai/home-stuff-ms.git
```

Go to the root of the project and run the command. </br>
This maven goal will not generate .jar file

```maven
mvn clean install
```

The profile prod will generate the .jar files
~100MB for each module

```maven
mvn clean install -P prod
```

Create the databases and tables, follow the schemas.sql in root

Build the docker compose file. </br>
Will lift Zipkin and KeyCloak

```docker
docker compose up -d
```

Finally, create the Realm and the Client as client_credentials Flow in KeyCloak Console

# Modules

- user
- nourishment
- consumption
- category
- role
- eureka-server
- auth
- api-gateway
- config-server

## Internal Packages

- security
- exception

By Kirenai 2024 [Kirenai](https://github.com/Kirenai)
