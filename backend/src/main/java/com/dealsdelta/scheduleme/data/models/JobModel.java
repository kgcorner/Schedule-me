package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.util.DateTimeUtil;
import org.springframework.data.annotation.Id;

import java.time.LocalTime;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

public class JobModel extends Job {
    @Id
    private String id;
    private String startTimeStr;

    @Override
    public String getJobId() {
        return id;
    }

    @Override
    public void setJobId(String jobId) {
        id = jobId;
    }

    @Override
    public void setStartTime(LocalTime startTime) {
        super.setStartTime(startTime);
        startTimeStr = DateTimeUtil.getTime(startTime);
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }
}