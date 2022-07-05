package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAudit {
    private String auditId;
    private String jobId;
    private Object job;
}