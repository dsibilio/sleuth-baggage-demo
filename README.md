# Getting Started

This is a sample project to showcase [Spring Cloud Sleuth](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html) baggage functionality in order to insert header data inside the MDC with no-code / low-code solutions.

You can interrogate an HTTP demo endpoint that will automatically insert the `http-custom-header` inside the MDC, if present:

`curl -H "http-custom-header: http sleuth baggage" http://localhost:8080/demo`

The underlying REST Controller will in turn send a Spring Integration message to a channel, triggering the insertion of all `spring.sleuth.baggage.local-fields` that can be found in the message headers, and are also marked as `correlation-fields`, as MDC key-value pairs.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.0/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

