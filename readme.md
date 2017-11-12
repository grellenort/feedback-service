## Synopsis

This project contains feedback service which is used to collect positive/neutral/negative feedback from customers

## Used technologies
JAVA 8, Spring boot, Spring MVC, Spring Test, Swagger, Mockito,

## Multilayer Architecture - MDC
### Model
Mostly dao classes.
### Domain
Business logic and services.
### Controller
Controller to process requests and create responses.

## Installation
### Via spring boot plugin
mvn spring-boot:run

### Via maven and pure java
mvn clean install

java -jar target/feedback-service-0.0.1-SNAPSHOT.jar

## API Reference

Via your favorite browser visit and enjoy.
http://localhost:8080/swagger-ui.html#/

## Tests

Test coverage should be above 80%. Cover use cases not only code.
Write integration test mixed with unit test to reach better maintainability.

## TODOs
* Authorization via Spring Security.
* Logging via Slf4j.
* Db layer via JPA and its implementation Hibernate.
* Some controller aspects to log detailed incoming requests.

## License

BSD 3-Clause License

Copyright (c) 2017, grellenort
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.