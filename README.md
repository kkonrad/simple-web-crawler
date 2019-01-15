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
[ ] Dockerfile

## Considerations

* Plan on using Spring Boot from the very begining. Probably overkill for this project.

## Potential extension points

* input data validation (is argument present, is valid URL)
* Scaling through messaging solution like Kafka and putting workers on Kubernetes alike platform
* try Favicon?
* don't use Internet in tests. Serve local webpage to make exact asserts in tests
* evaluate JS on pages to get more links (through Selenium?)
* don't download big/binary files etc.


