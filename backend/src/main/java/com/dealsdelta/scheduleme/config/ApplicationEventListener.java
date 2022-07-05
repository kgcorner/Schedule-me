package com.dealsdelta.scheduleme.config;


import com.dealsdelta.scheduleme.data.dao.*;
import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */
@Component
public class ApplicationEventListener  implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = Logger.getLogger(ApplicationEventListener.class);

    @Autowired
    JobProcessorExecutorService jobProcessorExecutorServicel;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("Application has been fully started");
        jobProcessorExecutorServicel.start();
    }
}