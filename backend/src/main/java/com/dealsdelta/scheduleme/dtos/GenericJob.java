package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericJob implements IJob {
    private String jobId;
    private String name;
    private boolean urgent;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private Map<String, Object> jobParams;
    private JOB_KIND jobKind;
}