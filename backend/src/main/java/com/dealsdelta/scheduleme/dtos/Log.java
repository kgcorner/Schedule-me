package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String VERBOSE = "VERBOSE";
    private Date date;
    private String module;
    private String message;
    private String jobId;
    private String level;
    private String runId;
}