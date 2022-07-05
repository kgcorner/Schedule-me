package com.dealsdelta.scheduleme.dtos;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

public interface IJob extends Serializable {
    enum JOB_KIND {
        MAIL_SENDER,
        FACEBOOK_POST_CREATOR,
        INSTAGRAM_POST_CREATOR
    }

    enum JOB_STATUS{
        IDLE,
        COMPLETED,
        RUNNING,
        WAITING,
        DUE,
        FAILED
    }
    String getName();
    boolean isUrgent();
    LocalTime getStartTime();
    String getStatus();
    String getJobId();
    Map<String, Object> getJobParams();
    JOB_KIND getJobKind();

    void setStatus(String status);
    default boolean isRepeatable() {
        return false;
    }
}