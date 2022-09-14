package com.dealsdelta.scheduleme.data.models;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class LogModelTest {
    @Test
    public void idTest() {
        String id = "id";
        LogModel model = new LogModel();
        model.setId(id);
        assertEquals(id, model.getId());
    }

}