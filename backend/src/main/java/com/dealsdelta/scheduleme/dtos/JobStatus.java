package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobStatus {
    private String jobId;
    private Date lastRun;
}