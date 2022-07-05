package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.DailyJob;
import org.springframework.data.annotation.Id;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

public class DailyJobModel extends DailyJob {
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