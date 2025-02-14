
db-setup: db-clean db-migrate

db-clean:
	docker compose up -d flyway-clean

db-migrate:
	docker compose up -d flyway-migrate

build-image-all: build-image-jvm build-image-native

build-image-jvm:
	./mvnw clean \
 	package \
 	spring-boot:build-image \
	-Dspring-boot.build-image.imageName=datadog-crash-jvm \
	-DskipTests

build-image-native:
	./mvnw clean \
	package \
	-P native \
	spring-boot:build-image \
	-Dspring-boot.build-image.imageName=datadog-crash-native \
	-DskipTests

start-app-jvm:
	docker compose up app-jvm

start-app-native:
	docker compose up app-native

todos-get:
	curl -X GET http://localhost:8080/todos

todos-add:
	curl -X POST http://localhost:8080/todos -H "Content-Type: application/json" -d '$(CONTENT)'