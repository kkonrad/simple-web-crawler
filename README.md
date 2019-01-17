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
Open in browser report file `build/reports/tests/test/index.html`

With coverage:
```
./gradlew test jacocoTestReport
```

Open in browser report file `build/reports/jacoco/test/html/index.html`


Enable E2E tests (might require `clean`):

1. Generate JavaDoc:
```
./gradlew javadoc
```

2. Go to `build/docs` folder and run:
```
python -m SimpleHTTPServer
```

3. Execute tests
```
./gradlew test jacocoTestReport
```

## Plan

- [x] define abstractions
- [x] implement core ones
- [ ] preapre E2E correctness check
- [ ] implement multithreaded version
- [ ] Dockerfile

## Considerations

* Plan on using Spring Boot from the very begining. Probably overkill for this project.
* Assuming that same domain means no acceptance for subdomains. It also implies the same protocol (either HTTP or HTTPS) and port.

## Potential extension points

* input data validation (is argument present, is valid URL)
* Scaling through messaging solution like Kafka and putting workers on Kubernetes alike platform
* try Favicon?
* don't use Internet in tests. Serve local webpage to make exact asserts in tests
* evaluate JS on pages to get more links (through Selenium?)
* don't download big/binary files etc.
* metrics collection (througput, errors, successes etc.)
* maybe skip # part in links as those are mostly about "internal" to webpage links


