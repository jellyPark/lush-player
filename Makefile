.PHONY: build build_artefact build_docker run_docker clean test

test:
	./gradlew test

build_artefact:
	docker run -u root --rm -v $(CURDIR)/.gradle-docker-cache:/home/gradle/.gradle -v $(CURDIR):/home/gradle/SERVICE_PATH -w /home/gradle/SERVICE_PATH gradle gradle -Dorg.gradle.daemon=false --stacktrace bootDistTar

build_artefact_docker:
	docker-compose run build-cache

run_docker:
	docker run --rm -ti -p 8080:8080 service

clean:
	docker run -u root --rm -v $(CURDIR)/.gradle-docker-cache:/home/gradle/.gradle -v $(CURDIR):/home/gradle/SERVICE_PATH -w /home/gradle/SERVICE_PATH gradle gradle -Dorg.gradle.daemon=false --stacktrace clean

docker-compose-up: build_artefact_docker
	docker-compose up --build

