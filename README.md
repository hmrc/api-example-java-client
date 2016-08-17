api-example-java-client
=======================

[![Build Status](https://travis-ci.org/hmrc/api-example-java-client.svg?branch=master)](https://travis-ci.org/hmrc/api-example-java-client) [ ![Download](https://api.bintray.com/packages/hmrc/releases/api-example-java-client/images/download.svg) ](https://bintray.com/hmrc/releases/api-example-java-client/_latestVersion)


*api-example-java-client* is a sample java application that provides a reference implementation of a HMRC client application.

It accesses three endpoints, each with their own authorisation requirements:

* Hello World - an Open endpoint that responds with the message “Hello World!”
* Hello Application - an Application-restricted endpoint that responds with the message “Hello Application!”
* Hello User - a User-restricted endpoint (accessed using an OAuth 2.0 token) that responds with the message “Hello User!”

The implementation of the Hello User flow requests an OAuth 2.0 token and subsequently uses that token to access the dummy secured endpoint.

The parameters `clientId`, `clientSecret` and `serverToken` will need to be updated in [`src/main/resources/application.properties`](src/main/resources/application.properties)

You will need to add the `Redirect URI` 'http://localhost:8080' to your application ('https://developer.service.hmrc.gov.uk/developer/applications/').

API documentation is available at https://developer.service.hmrc.gov.uk/api-documentation

Application developers need to register with the platform and will be provided with key, secret and tokens upon registration.

The server can be started with the following command:
```
mvn spring-boot:run
```

Once running, the application will be available at:

```
http://localhost:8080/
```

If you need to start your application on another port:

```
mvn spring-boot:run -Dserver.port=PORT
```


### Troubleshooting

- Make sure that you are using java 8, and that maven is configured to point to your java 8 installation.
```
mvn -v
```
should return something like:
```
Apache Maven 3.3.3
Maven home: /usr/share/maven
Java version: 1.8.0_101, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-oracle/jre
```

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
