package com.dealsdelta.scheduleme.data.models;

import com.dealsdelta.scheduleme.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/09/22
 */

public class JobModelTest {

    private JobModel jobModel;

    @Before
    public void setup() {
        jobModel = new JobModel();
    }

    @Test
    public void getJobId() {
        String jobId = "jobId";
        jobModel.setJobId(jobId);
        assertEquals(jobId, jobModel.getJobId());
    }

    @Test
    public void setStartTime() {
        LocalTime localTime = LocalTime.now();
        jobModel.setStartTime(localTime);
        assertEquals(localTime, jobModel.getStartTime());
        assertEquals(DateTimeUtil.getTime(localTime), jobModel.getStartTimeStr());
    }
}