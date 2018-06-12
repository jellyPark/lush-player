# NOTES
This repo is a simple Java web service with support for SQL connections. Initially this contains the MySQL connector library,  
but this might change in the future if we change our back end databases.

* Java 8
* Spring Boot 2 (2.0.0-M6)
* Gradle

## Usage
Use this tool `https://gitlab.platformserviceaccount.com/lush-soa/microservice-bootstrap` and select `javal` as the type of service to build  
it will create a new repo and copy the contents of this one into it.

The gradle wrapper can be used to run gradle commands locally.  
`./gradew bootDistTar` is run in the build pipeline to produce a single deployable Tar file that contains everything it needs to run.  
This command can be run locally and a tar will be built into `builds/distributions` dir.  

## Logging
LogLevel is controlled in `src/main/resources/application.properties`  
Logs should be written to `stdout` / `strerr` when running in Docker and not to a log file.  
Log messages should be kept to a minimum, the default log level in Production should be `ERROR`.
Please try to keep log message to a minimum. (`TRACE`, `DEBUG`, and `INFO` messages are fine for developement of course)