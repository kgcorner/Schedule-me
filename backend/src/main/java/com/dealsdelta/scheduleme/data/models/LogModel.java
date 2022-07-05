package com.dealsdelta.scheduleme.data.models;


import com.dealsdelta.scheduleme.dtos.Log;
import org.springframework.data.annotation.Id;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

public class LogModel extends Log {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}