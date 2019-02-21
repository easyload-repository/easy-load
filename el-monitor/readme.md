# 1: Testing
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
  java -jar el_monitor.jar
  mvn spring-boot:run
# according to profile
  mvn clean install -P qa
  mvn clean install -P uat
````
# 2: Run using docker
````
mvn package dockerfile:build
docker run -d -p 7777:7777 --name el_monitor el_monitor
````

