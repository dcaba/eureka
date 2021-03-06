package com.netflix.discovery.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.netflix.appinfo.AbstractInstanceConfig;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.archaius.Config;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.Ec2EurekaArchaius2InstanceConfig;
import com.netflix.discovery.EurekaArchaius2ClientConfig;
import com.netflix.discovery.EurekaArchaius2InstanceConfig;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.EurekaArchaius2TransportConfig;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import com.netflix.eventbus.impl.EventBusImpl;
import com.netflix.eventbus.spi.EventBus;

/**
 * Add this module to your project to enable Eureka client and registration
 *
 * @author elandau
 *
 */
public class Ec2EurekaClientModule extends AbstractModule {
    @Override
    protected void configure() {
        requireBinding(Config.class);

        bind(ApplicationInfoManager.class).asEagerSingleton();

        // Bindings for eureka
        bind(EurekaInstanceConfig.class).to(Ec2EurekaArchaius2InstanceConfig.class);
        bind(AbstractInstanceConfig.class).to(Ec2EurekaArchaius2InstanceConfig.class);
        bind(EurekaArchaius2InstanceConfig.class).to(Ec2EurekaArchaius2InstanceConfig.class);

        bind(EurekaTransportConfig.class).to(EurekaArchaius2TransportConfig.class);
        bind(EurekaClientConfig.class).to(EurekaArchaius2ClientConfig.class);

        bind(InstanceInfo.class).toProvider(EurekaConfigBasedInstanceInfoProvider.class);
        bind(EurekaClient.class).to(DiscoveryClient.class);

        bind(EventBus.class).to(EventBusImpl.class).in(Scopes.SINGLETON);
    }

    @Override
    public boolean equals(Object obj) {
        return Ec2EurekaClientModule.class.equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return Ec2EurekaClientModule.class.hashCode();
    }
}
