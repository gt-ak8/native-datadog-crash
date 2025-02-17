# Spring boot native with Datadog crashes at startup

This repository is a minimal example to reproduce a crash at startup when using Spring Boot native with Datadog.

## My environment
- I work on a Macbook Pro, with Apple M3 Pro chip.
- I build the image with Paketo Buildpacks, via the Spring boot integration

## How to reproduce

1. Clone this repository
2. Run `make db-setup` this will start a postgres container and create tables
3. Run `make build-image-all` this will build the native image and the jvm image, with Datadog enabled via the BP_DATADOG_ENABLED set to true in the pom.xml.
4. Run `make start-app-jvm` this will start the jvm image, you can see in the logs that datadog is enabled and the application starts correctly.
5. Stop the jvm image
6. Run `make start-app-native` this will start the native image, you can see in the logs that datadog is enabled and the application crashes at startup.