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


