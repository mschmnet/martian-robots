# Martian Robots

## How to build it and execute the tests

Java 11 is required (Java 16 doesn't work as Gradle doesn't support it yet)

	./gradlew clean build

## How to run it

### Spring Boot application with WebSockets (locally)

	java -jar build/libs/martian-robots-*.jar spring-boot

Then, open the app in [http://localhost:8080](http://localhost:8080)

### Spring Boot application with WebSockets (locally with Docker)

	# Docker build command is optional, since the image is already pushed to Docker Hub
	docker build -t mschmnet/martian-robots:0.1 -f Dockerfile build/libs/
	docker run -d --rm --name martian-robots -p 8080:8080 mschmnet/martian-robots:0.1

### CLI application

	java -jar build/libs/martian-robots-*.jar cli < input.txt

Or, if you want

	java -jar build/libs/martian-robots-*.jar cli << EOF
	5 3
	1 1 E
	RFRFRFRF
	3 2 N
	FRRFLLFFRRFLL
	0 3 W
	LLFFFLFLFL
	EOF

### Run an already deployed application

[http://manuelschmidt.net:8090/](http://manuelschmidt.net:8090/)
