package com.smattme.eureka.client.wrapper;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.PropertiesInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.smattme.eureka.client.wrapper.config.CustomEurekaClientConfig;
import com.smattme.eureka.client.wrapper.config.WebAppInstanceConfig;
import com.smattme.eureka.client.wrapper.util.ConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EurekaClientService {

    private CustomEurekaClientConfig eurekaClientConfig;
    private WebAppInstanceConfig webAppInstanceConfig;
    private EurekaClient eurekaClient;
    private Logger logger = LoggerFactory.getLogger(EurekaClientService.class);
    private Properties properties;
    private static final String CONFIG_NAME = "eureka-client";


    public EurekaClientService() {
        properties = ConfigurationUtil.loadCascadedProperties(CONFIG_NAME);
        this.webAppInstanceConfig = new WebAppInstanceConfig(properties);
        this.eurekaClientConfig = new CustomEurekaClientConfig();
    }

    protected ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        return new ApplicationInfoManager(instanceConfig, instanceInfo);
    }

    protected void initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
    }


    /**
     * this is the entry point for registering
     * the app with the Eureka Server
     */
    public void registerInstance() {
        logger.info("registering this app with eureka server");
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(webAppInstanceConfig);
        initializeEurekaClient(applicationInfoManager, eurekaClientConfig);
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }


    public void deRegister() {
        logger.info("shutting down eureka client and de-registering this instance from the server");
        if(eurekaClient != null)
            eurekaClient.shutdown();
    }

    /**
     * this should be called after calling the
     * registerInstance() method which will instantiate the
     * client.
     * Otherwise, it may return NULL
     * @return eurekaClient
     */
    public EurekaClient getEurekaClient() {
        return eurekaClient;
    }

}
