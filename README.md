Eureka Client Wrapper
=====================
This is a wrapper for easily connecting non-spring-boot Java projects into Spring Cloud Eureka Server

Usage
=====
- Instantiate the EurekaClientService class `EurekaClientService eurekaClientService = new EurekaClientService();`
- When your application is starting up register the instance with the configured Eureka Server thus:

```
eurekaClientService.registerInstance();
```

- When your application is shutting down, de-register the instance from the configured Eureka Server:

```
eurekaClientService.deRegister();
```

NOTE: It's advisable to instantiate the `EurekaClientService` as a Bean that can be re-used system-wide

Config
------
Make sure you have a config file called `eureka-client.properties` in the classpath (typically the `resources` folder) of the application.

Example content of `eureka-client.properties`:

```
##Eureka Client configuration for Eureka Service
#reference: https://github.com/Netflix/eureka/blob/master/eureka-server/src/main/resources/eureka-client.properties

eureka.region=default
eureka.name=session-manager
eureka.vipAddress=session-manager
eureka.port=8181
eureka.preferSameZone=false
eureka.shouldUseDns=false
eureka.serviceUrl.default=http://localhost:9003/eureka/
eureka.shouldOnDemandUpdateStatusChange=false
eureka.shouldFilterOnlyUpInstances=false
eureka.hostname=localhost #this is very important for SpringBoot Feign client communication. It should be the hostname of the server where the app is running e.g. example.com
eureka.homePageUrl=http://localhost:8181/
eureka.healthCheckUrl=http://localhost:8181/health
eureka.statusPageUrl=http://localhost:8181/metrics
```
