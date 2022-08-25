package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.Job;
import org.springframework.data.annotation.Id;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

public class JobModel extends Job {
    @Id
    private String id;

    @Override
    public String getJobId() {
        return id;
    }

    @Override
    public void setJobId(String jobId) {
        id = jobId;
    }
}