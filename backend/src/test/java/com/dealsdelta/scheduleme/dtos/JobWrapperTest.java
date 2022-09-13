package com.dealsdelta.scheduleme.dtos;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class JobWrapperTest {

    @Test
    public void putRecord() {
        Object oldRec = new Object();
        Object newRecord = new Object();
        int count = 8;
        Map<Object, Object> records = new HashMap<>();
        for (int i = 0; i < count; i++) {
            records.put(oldRec, newRecord);
        }
        JobWrapper jobWrapper = new JobWrapper();
        for(Map.Entry<Object, Object> entries : records.entrySet()) {
            jobWrapper.putRecord(entries.getKey(), entries.getValue());
        }
        Map<Object, Object> result = jobWrapper.getRecords();
        assertEquals(records.size(), result.size());
        for(Map.Entry<Object, Object> entries : result.entrySet()) {
            assertEquals(newRecord, entries.getKey());
            assertEquals(oldRec, entries.getValue());
        }
    }
}