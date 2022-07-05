package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.JobAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 28/06/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAuditModel extends JobAudit {
    @Id
    private String id;

    @Override
    public String getAuditId() {
        return id;
    }

    @Override
    public void setAuditId(String auditId) {
        this.id = auditId;
    }
}