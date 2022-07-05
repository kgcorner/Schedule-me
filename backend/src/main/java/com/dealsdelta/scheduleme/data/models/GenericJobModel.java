package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.GenericJob;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

public class GenericJobModel extends GenericJob  {
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