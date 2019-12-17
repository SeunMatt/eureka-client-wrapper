Eureka Client Wrapper
=====================
This is a wrapper for easily connecting non-spring-boot Java projects into Spring Cloud Eureka Server

Usage
=====

Dependency
-----------
To add this as a dependency:

- Clone https://github.com/SeunMatt/eureka-client-wrapper.git
- run `cd eureka-client-wrapper`
- run `mvn clean install`

Now, add the following dependency to your pom.xml:

```
<dependency>
    <groupId>com.smattme</groupId>
    <artifactId>eureka-client-wrapper</artifactId>
    <version>1.0.0</version>
</dependency>
```

Config
------
Make sure you have a config file called `eureka-client.properties` in the classpath (typically the `resources` folder) of the application.

Example content of `eureka-client.properties`:

```
##Eureka Client configuration for Eureka Service
#reference: https://github.com/Netflix/eureka/blob/master/eureka-server/src/main/resources/eureka-client.properties

eureka.region=default
eureka.name=example-app #this should be the unique name of the service
eureka.vipAddress=example-app #this should be the unique name of the service and is what clients like OpenFeign will use to reference this app
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

Registering and De-Registering with a Eureka Server
----------------------------------------------------

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

Getting the HomePage URL of a remote service
===========================================
One of the huge benefits of using a Discovery Server is never having to hard-code the URL of another service.

Normative architecture dictate that each service, in a microservice environment, will have a unique name.

This name is configured as the `eureka.vipAddress`  as mentioned above. Using the vipAddress name, we can get the instance information

 of a remote service - including the homepage URL - which is a combination of the hostname and port.

 This simply means each service only need to know the URL path and not necessarily the BASE URL of their counterparts.

 To get the homepage URL:

 ```
 String serviceHomepageURL = eurekaClientService.getRemoteServiceHomepageURL(vipAddress);
 Map response = restTemplate.getForObject(serviceHomepageURL + "path/in/remote/service", Map.class);
 ```

