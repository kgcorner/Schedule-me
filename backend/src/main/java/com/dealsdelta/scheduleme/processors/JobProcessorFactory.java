package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.dtos.JOB_KIND;
import com.dealsdelta.scheduleme.dtos.Job;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */

public class JobProcessorFactory {
    private static final Map<JOB_KIND, Class> jobProcessorMap = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(JobProcessorFactory.class);

    static {
        ServiceLoader<JobProcessor> processorServiceLoader = ServiceLoader.load(JobProcessor.class);
        for(JobProcessor processor : processorServiceLoader) {
            for(JOB_KIND kind : JOB_KIND.values()) {
                if(processor.supports(kind)) {
                    jobProcessorMap.put(kind, processor.getClass());
                    break;
                }
            }
        }
    }

    public static JobProcessor getJobProcessor(Job job) {
        Class requiredJobProcessorType = null;
        if(jobProcessorMap.containsKey(job.getJobKind())) {
            requiredJobProcessorType = jobProcessorMap.get(job.getJobKind());
        }
        try {
            if(requiredJobProcessorType != null)
                return (JobProcessor) Class.forName(requiredJobProcessorType.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }
}