# Simple web crawler


## Requirements

* Java 8
* Linux machine (should also work on Windows through `gradlew.bat`


## Usage

Starting app:

```
./gradlew bootRun
```


Tests:

```
./gradlew test
```


## Plan

[ ] define abstractions
[ ] implement core ones
[ ] preapre E2E correctness check
[ ] implement multithreaded version

## Considerations

* Plan on using Spring Boot from the very begining. Probably overkill for this project.

## Potential extension points

* Scaling through messaging solution like Kafka and putting workers on Kubernetes alike platform

