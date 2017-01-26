# CI Status
[![Build Status](https://travis-ci.org/sflpro/ms_payment.svg?branch=master)](https://travis-ci.org/sflpro/ms_payment)
# Payment Microservice

The payment microservice is a separatable deployable component intended for processing payment transcations.
It provides high level abstraction layer on top of the actual payment providers by exposing provider agnostic  API. Currently the microservice supports following payment providers :
* [Adyen](https://www.adyen.com/)

The main features are :
* Payments implemented via a redirect to the payment provider platform
* Direct payments by using client side ecnryption feature exposed by the payment provider
* Secure recurring/saved payment methods utilizing
* Payment transactions history

## Microservice API

The public API of the microservice is exposed via HTTP REST. API client libraries are available in the followign languages :
* Java

However, any platform supporting HTTP calls can use the microservice by manually implementing HTTP calls execution logic.

## Deployment

Currently the microservice is packaged as a WAR file, hence requiring a servlet container which can be used for running it. Tested containers are :
* [Apache Tomcat 8] (http://tomcat.apache.org/)
* [Jetty](https://eclipse.org/jetty/)

## TODOs

Below you may find some of the actions planned for the near future :
* Convert the application to use [Spring Boot/Cloud](http://projects.spring.io/spring-cloud/)
* Document the REST API in [Apiary](https://apiary.io/)
* Add client libraries in Python
* Add support for other SMS providers
* Expose the API via [GRPC](http://www.grpc.io/)  
