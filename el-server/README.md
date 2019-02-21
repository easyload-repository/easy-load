# Easy Load Backend

## Document

[API](./doc/api.md)

## Testing
````
# for integration only
  mvn failsafe:integration-test
# for unit only
  mvn clean test
# for all testing
  mvn clean integration-test
  mvn clean install
# skip test
  mvn clean install -Dmaven.test.skip=true
# run
  java -jar el_project.jar
  mvn spring-boot:run
# according to profile
  mvn clean install -P qa
  mvn clean install -P uat
````
## Run using docker
````
mvn package dockerfile:build
docker run -d -p 7070:7070 --name el_project el_project
````

