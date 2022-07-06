package com.dealsdelta.scheduleme.dtos;


import com.dealsdelta.scheduleme.data.models.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAudit implements Serializable {
    private String auditId;
    private String jobId;
    private Object job;
    private Date startTime;
    private Date endTime;
    private String status;
    private List<LogModel> logs;
}