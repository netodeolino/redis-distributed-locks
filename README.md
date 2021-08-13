#Distributed redis locks

## Run
- docker-compose up --build -d
- mvn spring-boot:run

## Endpoints
- http://localhost:8080/test/set?key={value}
- http://localhost:8080/test/lock?key={value}