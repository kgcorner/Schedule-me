package com.dealsdelta.scheduleme.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

@Data
@NoArgsConstructor
public class Job implements Serializable {
    private String jobId;
    private String jobName;
    private RUN_FREQUENCY frequency;
    private Map<String, Object> jobParams;
    private boolean urgent;
    private LocalTime startTime;
    private JOB_STATUS status;
    private JOB_KIND jobKind;
    private Date lastRunStartTime;
    private Date lastRunEndTime;
    private int lastRunRecordCount;
    private List<Object> processedRecordsOfLastRun;
    private List<Object> missedRecordsOfLastRun;
    private int dayOfWeek;
    private int[] daysOfWeek;
    private int dayOfMonth;
    private int[] daysInMonth;
}