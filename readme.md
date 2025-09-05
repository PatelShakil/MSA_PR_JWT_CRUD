# MSA CRUD WITH JWT

This document provides a step-by-step guide to setting up and running a microservices-based application with CRUD functionality and JWT authentication.

## Project Setup

1.  Create two Maven projects: `MSAClient` and `MSAResource`. Use the `jakarta-9-microprofile` archetype.
2.  In both projects, update the `pom.xml` to use Jakarta EE 10 and MicroProfile 6.
3.  Perform a project-wide "find and replace" to change `javax` to `jakarta`.

## MSAResource Configuration

1.  **Persistence:** In `MSAResource`, configure the connection pool in `persistence.xml`.
2.  **Entity:** Create a `User` entity with the following attributes: `id`, `name`, `email`, `phone`.
3.  **EJB:** Implement CRUD operations in an EJB (`UserBean.java`).
4.  **Service:** Create a `UserService.java` to expose the EJB methods.
5.  **Authorization:** Apply role-based access control using the `@RolesAllowed("chief")` annotation.
6.  **Bootstrap:** In `Bootstrap.java`, configure the authentication method and declare roles:

    ```java
    @LoginConfig(authMethod = "MP-JWT")
    @DeclareRoles({"chief", "admin"})
    ```

7.  **MicroProfile Config:** Create a `microprofile-config.properties` file in `src/main/resources/META-INF/`.
8.  **JWT Setup:**
    *   Download `jwtenizr.jar` and run `java -jar jwtenizr.jar`.
    *   Copy the generated JWT token from `token.jwt`.
    *   Copy the content of the generated `microprofile-config.properties` and paste it into your project's `microprofile-config.properties` file.

## MSAClient Configuration

1.  **REST Client:** Create a `UserClient` interface with the following configuration:

    ```java
    @RegisterRestClient(baseUri = "http://localhost:8085/MSAResource/rest/user")
    ```

2.  **Authorization Header:** In the `UserClient` interface, add a method to provide the JWT token in the Authorization header for all requests:

    ```java
    @ClientHeaderParam(name = "Authorization", value = "{getToken}")
    @RolesAllowed("chief")

    default String getToken() {
        Config config = ConfigProvider.getConfig();
        String token = "Bearer " + config.getValue("jwt", String.class);
        return token;
    }
    ```

3.  **MicroProfile Config:** Create a `microprofile-config.properties` file in `src/main/resources/META-INF/`.
4.  **JWT Token:** Add the JWT token to the `microprofile-config.properties` file:

    ```properties
    jwt=<Token copied from token.jwt>
    ```

5.  **Servlet:** In your servlet, inject the `UserClient` and use its methods to interact with the `MSAResource` service.

## Running the Application

1.  **Run MSAResource:**

    ```bash
    java -jar payara.jar --deploy MSAResource/artifact/MSAResource.war --port 8085 --addlibs mysql-connector-java-8.0.20.jar --domainconfig domain.xml
    ```

2.  **Run MSAClient:**

    ```bash
    java -jar payara.jar --deploy MSAClient/artifact/MSAClient.war --port 8086
    ```

---

*Created by: MUHAMMADSHAKIL PATEL*