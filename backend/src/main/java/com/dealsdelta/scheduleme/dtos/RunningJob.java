package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunningJob implements Serializable {
    private String runningJobId;
    private String jobId;
    private Date startedAt;
    private IJob job;
}