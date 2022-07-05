package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class RepeatableJob implements IJob {
    private String jobId;
    private String name;
    private boolean urgent;
    private LocalTime startTime;
    private String status;
    private Map<String, Object> jobParams;
    private JOB_KIND jobKind;
    private Date lastCompletedRunStartedAt;
    private Date lastCompletedRunEndedAt;

    @Override
    public boolean isRepeatable() {
        return true;
    }
}