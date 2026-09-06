# GitHub Gist Service

A small Spring Boot service that returns the publicly available Gists for a GitHub user.

## Requirements

- Java 17+
- Maven 3.6+
- Docker

## Run locally

Build:

```bash
mvn clean package
```

Run:

```bash
java -jar target/gist-service-0.0.1-SNAPSHOT.jar
```

The service listens on port 8080.

Example:

```bash
curl http://localhost:8080/octocat
```

The service calls GitHub's public Gist API for the requested user.

## Tests

Run:

```bash
mvn test
```

The controller tests mock the GitHub-facing service, so tests do not depend on GitHub being available.

The tests cover a normal response, an empty Gist list, a user not found response and a GitHub availability failure.

## Docker

The Dockerfile uses a multi-stage build. Maven is used only in the build stage and the runtime image contains only the application JAR and Java runtime.

Build:

```bash
docker build -t gist-service .
```

Run:

```bash
docker run --rm -p 8080:8080 gist-service
```

Then:

```bash
curl http://localhost:8080/octocat
```

## Notes

Public GitHub Gists can be read without authentication. For a production deployment I would consider authenticated GitHub API access, since unauthenticated REST API calls are rate limited.
