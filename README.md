api-example-java-client
=======================

[![Build Status](https://travis-ci.org/hmrc/api-example-java-client.svg?branch=master)](https://travis-ci.org/hmrc/api-example-java-client) [ ![Download](https://api.bintray.com/packages/hmrc/releases/api-example-java-client/images/download.svg) ](https://bintray.com/hmrc/releases/api-example-java-client/_latestVersion)


*api-example-java-client* is a Spring based java application that provides a reference implementation of a HMRC client application.

It accesses three endpoints, each with their own authorisation requirements:

* Hello World - an Open endpoint that responds with the message “Hello World!”
* Hello Application - an Application-restricted endpoint (accessed using OAuth 2.0 client-credentials flow) that responds with the message “Hello Application!”
* Hello User - a User-restricted endpoint (accessed using OAuth 2.0 authorization-code flow) that responds with the message “Hello User!”

The implementation of the Hello User flow requests an OAuth 2.0 access token and subsequently uses that token to access the secured endpoint.

Application developers need to register with the HMRC Developer Hub ('https://developer.service.hmrc.gov.uk').

Once this is done they will need to create an application and subscribe to the Hello World test applicaion.

The client id and client secret for this application can be obtained from the applications `Client ID` and `Client secrets` menu items.

The `client-id` and `client-secret` for the application will need to be added to [`src/main/resources/application.yml`](src/main/resources/application.yml)

You will need to add the `Redirect URI` 'http://localhost:8080' to your HMRC Developer Hub application ('https://developer.service.hmrc.gov.uk/developer/applications/').

API documentation is available at https://developer.service.hmrc.gov.uk/api-documentation


The server can be started with the following command:
```
./gradlew bootRun
```

Once running, the application provides the folllowing endpoints:

```
http://localhost:8080/hello-world
http://localhost:8080/hello-application
http://localhost:8080/hello-user
```

### Troubleshooting

- Make sure that you are using java 8 ensure that gradle is configured for your java 8 installation.


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
