package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.RunningJob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 26/06/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunningJobModel extends RunningJob {
    @Id
    private String id;

    @Override
    public String getRunningJobId() {
        return id;
    }

    @Override
    public void setRunningJobId(String runningJobId) {
        this.id = runningJobId;
    }
}