package com.dealsdelta.scheduleme.processors;


import com.dealsdelta.scheduleme.dtos.JOB_KIND;
import com.dealsdelta.scheduleme.dtos.Job;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */

public class JobProcessorFactory {
    private static final Map<JOB_KIND, JobProcessor> jobProcessorMap = new HashMap<>();

    static {
        ServiceLoader<JobProcessor> processorServiceLoader = ServiceLoader.load(JobProcessor.class);
        for(JobProcessor processor : processorServiceLoader) {
            for(JOB_KIND kind : JOB_KIND.values()) {
                if(processor.supports(kind)) {
                    jobProcessorMap.put(kind, processor);
                    break;
                }
            }
        }
    }

    public static JobProcessor getJobProcessor(Job job) {
        if(jobProcessorMap.containsKey(job.getJobKind())) {
            return jobProcessorMap.get(job.getJobKind());
        }
        return null;
    }
}