package com.smattme.eureka.client.wrapper.config;

import com.netflix.discovery.DefaultEurekaClientConfig;

import java.util.Collections;
import java.util.List;


public class CustomEurekaClientConfig extends DefaultEurekaClientConfig {

    //minimum change needed override
    @Override
    public List<String> getEurekaServerServiceUrls(String myZone) {
        return Collections.singletonList("http://localhost:9003/eureka");
    }



}
