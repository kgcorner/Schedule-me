package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/07/22
 */

@Data
@NoArgsConstructor
public class JobWrapper {
    private RunningJob runningJob;
    private Map<Object, Object> records = new HashMap<>();

    public  void putRecord(Object oldRecord, Object newRecord) {
        records.put(newRecord, oldRecord);
    }
}