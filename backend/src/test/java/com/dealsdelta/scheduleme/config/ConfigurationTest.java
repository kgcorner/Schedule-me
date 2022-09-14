package com.dealsdelta.scheduleme.config;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class ConfigurationTest {

    @Test
    public void addCorsMappings() {
        Configuration configuration = new Configuration();
        CorsRegistry corsRegistry = PowerMockito.mock(CorsRegistry.class);
        CorsRegistration corsRegistration = PowerMockito.mock(CorsRegistration.class);
        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        configuration.addCorsMappings(corsRegistry);
        Mockito.verify(corsRegistration, Mockito.times(1))
            .allowedMethods("GET", "POST","DELETE", "PUT", "PATCH");
    }
}